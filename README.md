# Project weather

#### A web application for viewing the current weather. Users can register and add one or more locations (cities, villages, and other locations) to their collection, after which the application's main page displays a list of locations with their current weather.

## Features

- **Frontend**: HTML/CSS, 
- **Backend**: Spring MVC, HTTP GET and POST requests,Thymeleaf, cookies
- **Database**: SQL, Hibernate, Migration FlyWay
- **Tests**: integration tests, JUnit5


## Project motivation

- Using cookies and sessions to authenticate users
- Getting to know Spring
- Working with external APIs

## Application functionality
#### Working with users :
* Registration
* Authorization
* Logout

#### Working with location :
* Search
* Add in list
* View a list of locations, for each location the name and temperature are displayed
* Delete from list


## Working with sessions and cookies

Implemented user authorization and allowed the browser to “remember” whether the current user is authorized

When a user logs in, the backend application creates a session with an identifier and sets this identifier in the HTTP response cookie, which the application uses to respond to the POST request of the authorization form. The session also contains the ID of the logged-in user.


#### Service create and expire session
``` 
public String createSessions(UsersDto usersDto) {
        LocalDateTime localDateTime = LocalDateTime.now();
        UUID uuid = UUID.randomUUID();
        if (usersRepository.findById(usersDto.getId())==null) {
            throw new IllegalArgumentException("user is absent");
        }else {
            Users user = usersRepository.findById(usersDto.getId());
            Sessions sessions = new Sessions(uuid, localDateTime.plusMinutes(30), user);
            sessionsRepository.save(sessions);
        }
        return uuid.toString();
    }

    public boolean expireSession(String sessionValue) {
        UUID sessionId = UUID.fromString(sessionValue);
        LocalDateTime localDateTime = LocalDateTime.now();

        Sessions session=sessionsRepository.findById(sessionId);
        if (session==null) {
            return true;
        }
        else {
            return getSessionRelevance(localDateTime, session);
        }

    }
``` 

#### Example save cookies
```
  @PostMapping("/login-sign-up")
    public String registrationUser(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("repeatPassword") String repeatPassword, HttpServletResponse response) {

        if (password.equals(repeatPassword)) {
            UsersDto usersDto = usersService.addUsers(username, password);
            String uuid = sessionsService.createSessions(usersDto);
            Cookie cookie = new Cookie("id", uuid);
            response.addCookie(cookie);
        } else {
            return "sign-up-with-errors";
        }

        return "redirect:/index";
    }
```


## Spring MVC

Used main components of spring: repository, controller, services, configuration

#### Example spring configuration

```
@Getter
@Setter
@Configuration
public class WeatherConfiguration {

    @Value("${weather.api.baseUrl}")
    private String baseUrl;

    @Value("${weather.api.key}")
    private String key;


    @Bean
    public WebClient beanRestTemplate() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}
```


## Database


#### Table `Users`
| Колонка     | Тип     | Комментарий |
|-------------|---------|-------------|
| `ID`        | int     | User ID, auto-increment, primary key |
| `Login`     | varchar | User login — username or email |
| `Password`  | varchar | Hashed password|


#### Table `Locations`

| Колонка     | Тип      | Комментарий |
|-------------|----------|-------------|
| `ID`        | int      | User ID, auto-increment, primary key |
| `Name`      | varchar  | Location name |
| `UserId`    | int      | The user who added this location |
| `Latitude`  | decimal  | Latitude |
| `Longitude` | decimal  | Longitude |


#### Table `Sessions`
| Колонка     | Тип      | Комментарий |
|-------------|----------|-------------|
| `ID`        | UUID     | Unique session identifier, primary key |
| `UserId`    | int      | The user who owns the session |
| `ExpiresAt` | datetime | Session expiration time (creation time + N hours) |


## Getting weather information using the OpenWeatherMap API
Integration and implementation with the OpenWeather API was implemented

#### Enable API OpenWeather
```
weather:
  api:
    baseUrl: https://api.openweathermap.org
    key: 7a5864eb5cac7c74e9bc2a2e3a5023bf
```

#### Example service for realization API OpenWeather
```
    public List<GeoCityDto> getGeoCity(String city) {

        try {
            String jsonpObject = webClient.get().
                    uri(uriBuilder -> uriBuilder
                            .path("/geo/1.0/direct")
                            .queryParam("q", city)
                            .queryParam("limit", 5)
                            .queryParam("appid", weatherConfiguration.getKey())
                            .build())
                    .retrieve()
                    .bodyToMono(String.class).block();


            System.out.println(jsonpObject);
            JsonNode locations = objectMapper.readTree(jsonpObject);
            System.out.println(locations);

            if (!locations.isArray() || locations.isEmpty()) {
                throw new IllegalStateException("Город не найден в ответе OpenWeather");
            }

            List<Optional<GeoCityDto>> geoCityDtoList = new ArrayList<>();
            for (JsonNode node : locations) {
                GeoCityDto geoCityDto = new GeoCityDto();
                geoCityDto.setCity(node.path("name").asText());
                geoCityDto.setCountry(node.path("country").asText());
                geoCityDto.setLatitude(node.path("lat").asDouble());
                geoCityDto.setLongitude(node.path("lon").asDouble());

                geoCityDtoList.add(Optional.of(geoCityDto));
            }

            List<GeoCityDto> unpackedWeatherDtos = geoCityDtoList.stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();

            System.out.println(unpackedWeatherDtos);
            return unpackedWeatherDtos;
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Город не найден в ответе OpenWeather");
        } catch (WebClientResponseException e) {
            throw new IllegalStateException("Город не найден в ответе OpenWeather");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
```
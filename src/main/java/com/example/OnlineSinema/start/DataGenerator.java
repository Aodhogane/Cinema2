//package com.example.OnlineSinema.start;
//
//import com.example.OnlineSinema.domain.*;
//import com.example.OnlineSinema.repository.*;
//import com.github.javafaker.Faker;
//import com.example.OnlineSinema.enums.Genres;
//import com.example.OnlineSinema.enums.UserRoles;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.time.ZoneId;
//import java.util.HashSet;
//import java.util.concurrent.TimeUnit;
//
//@Component
//public class DataGenerator {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private ClientRepository clientRepository;
//
//    @Autowired
//    private ActorRepository actorsRepository;
//
//    @Autowired
//    private DirectorsRepository directorsRepository;
//
//    @Autowired
//    private FilmRepository filmRepository;
//
//    @Autowired
//    private ReviewRepository reviewsRepository;
//
//    private Faker faker = new Faker();
//
//    @PostConstruct
//    public void generateData() {
//        // Генерация пользователей
//        for (int i = 0; i < 10; i++) {
//            String email = faker.internet().emailAddress();
//            String password = faker.internet().password();
//            UserRoles role = UserRoles.values()[faker.random().nextInt(UserRoles.values().length)];
//
//            User user = new User(email, password, role);
//            userRepository.create(user); // Сохраняем пользователя
//
//            // Генерация клиентов, актеров, режиссеров в зависимости от роли
//            if (role == UserRoles.CLIENT) {
//                Client client = new Client(faker.name().fullName(), faker.internet().emailAddress(), new HashSet<>());
//                client.setUser(user);
//                clientRepository.create(client);
//            } else if (role == UserRoles.ACTOR) {
//                Actors actor = new Actors(faker.name().firstName(), faker.name().lastName(), faker.name().fullName(), new HashSet<>(), null);
//                actor.setUser(user);
//                actorsRepository.create(actor);
//            } else if (role == UserRoles.DIRECTOR) {
//                // Генерация режиссера, привязка пользователя
//                Directors director = new Directors(faker.name().firstName(), faker.name().lastName(), faker.name().fullName(), new HashSet<>());
//                director.setUser(user); // Привязываем пользователя к директору
//                directorsRepository.create(director); // Сохраняем режиссера
//            }
//        }
//
//        // Генерация фильмов
//        for (int i = 0; i < 20; i++) {
//            Film film = new Film(
//                    faker.name().title(),
//                    faker.date().past(1000, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
//                    faker.random().nextDouble() * 10,
//                    directorsRepository.findAll(Directors.class, 1, 10).getContent().get(faker.random().nextInt(10)),  // Используем findAll для получения данных
//                    Genres.values()[faker.random().nextInt(Genres.values().length)],
//                    new HashSet<>(),
//                    new HashSet<>()
//            );
//            filmRepository.create(film);
//        }
//
//        // Генерация отзывов
//        for (int i = 0; i < 10; i++) {
//            Reviews review = new Reviews(
//                    faker.lorem().sentence(),
//                    faker.number().numberBetween(1, 10),
//                    faker.date().past(500, java.util.concurrent.TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime()
//            );
//            review.setClient(clientRepository.findAll(Client.class, 1, 10).getContent().get(faker.random().nextInt(10))); // Используем findAll
//            review.setFilm(filmRepository.findAll(Film.class, 1, 10).getContent().get(faker.random().nextInt(10))); // Используем findAll
//            reviewsRepository.create(review);
//        }
//    }
//}
//package com.example.OnlineSinema.start;
//
//import com.example.OnlineSinema.domain.*;
//import com.example.OnlineSinema.repository.*;
//import com.github.javafaker.Faker;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.TimeUnit;
//
//@Component
//@Transactional
//public class DataInitializer implements CommandLineRunner {
//
//    private final Faker faker;
//    private final ActorRepository actorRepository;
//    private final DirectorRepository directorRepository;
//    private final FilmRepository filmRepository;
//    private final GenreRepository genreRepository;
//    private final ReviewsRepository reviewRepository;
//    private final TicketRepository ticketRepository;
//    private final UserRepository userRepository;
//    private final AccessRepository accessRepository;
//    private final BCryptPasswordEncoder passwordEncoder;
//    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
//
//    @Autowired
//    public DataInitializer(Faker faker, ActorRepository actorRepository,
//                           DirectorRepository directorRepository, FilmRepository filmRepository,
//                           GenreRepository genreRepository, ReviewsRepository reviewRepository,
//                           TicketRepository ticketRepository, UserRepository userRepository,
//                           AccessRepository accessRepository, BCryptPasswordEncoder passwordEncoder) {
//        this.faker = faker;
//        this.actorRepository = actorRepository;
//        this.directorRepository = directorRepository;
//        this.filmRepository = filmRepository;
//        this.genreRepository = genreRepository;
//        this.reviewRepository = reviewRepository;
//        this.ticketRepository = ticketRepository;
//        this.userRepository = userRepository;
//        this.accessRepository = accessRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    @Transactional
//    public void run(String... args) throws Exception {
//        logger.info("Начало инициализации данных...");
//
//        // Создание пользователей
//        // Создание и сохранение Access для администратора
//        Access adminAccess = new Access("ADMIN");
//        accessRepository.save(adminAccess);
//
//        User adminUser = new User();
//        adminUser.setUsername("Admin");
//        adminUser.setEmail("admin@example.com");
//        adminUser.setPassword(passwordEncoder.encode("admin"));
//        adminUser.setAccess(adminAccess);
//        userRepository.save(adminUser);
//        logger.info("Администратор создан: {}", adminUser);
//
//        // Создание и сохранение Access для обычных пользователей
//        Access userAccess = new Access("USER");
//        accessRepository.save(userAccess);
//
//        for (int i = 0; i < 5; i++) {
//            User user = new User();
//            user.setUsername(faker.name().username());
//            user.setEmail(faker.internet().emailAddress());
//            user.setPassword(passwordEncoder.encode(faker.internet().password()));
//            user.setAccess(userAccess);
//            userRepository.save(user);
//            logger.info("Пользователь создан: {}", user);
//        }
//
//        // Создание жанров
//        List<Genres> genres = List.of(
//                new Genres("Action"),
//                new Genres("Comedy"),
//                new Genres("Drama"),
//                new Genres("Horror"),
//                new Genres("Sci-Fi")
//        );
//        genres.forEach(genreRepository::save);
//        logger.info("Жанры созданы: {}", genres);
//
//        // Создание актеров
//        List<Actors> actors = List.of(
//                new Actors("Tom", "Hanks", "Wilson"),
//                new Actors("Leonardo", "DiCaprio", "None"),
//                new Actors("Scarlett", "Johansson", "None"),
//                new Actors("Robert", "Downey Jr.", "None"),
//                new Actors("Chris", "Evans", "None"),
//                new Actors("Chris", "Hemsworth", "None"),
//                new Actors("Benedict", "Cumberbatch", "None"),
//                new Actors("Emma", "Stone", "None"),
//                new Actors("Brad", "Pitt", "None"),
//                new Actors("Angelina", "Jolie", "None")
//        );
//        actors.forEach(actorRepository::save);
//        logger.info("Актеры созданы: {}", actors);
//
//        // Создание режиссеров
//        List<Directors> directors = List.of(
//                new Directors("Christopher", "Nolan", "None"),
//                new Directors("Quentin", "Tarantino", "None"),
//                new Directors("Martin", "Scorsese", "None"),
//                new Directors("James", "Cameron", "None"),
//                new Directors("Steven", "Spielberg", "None")
//        );
//        directors.forEach(directorRepository::save);
//        logger.info("Режиссеры созданы: {}", directors);
//
//        // Создание фильмов
//        for (int i = 0; i < 20; i++) {
//            Film film = new Film();
//            film.setTitle(faker.gameOfThrones().character());
//            film.setExitDate(faker.date().past(10, TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
//            film.setDuration(faker.number().numberBetween(60, 180));
//            film.setRating(faker.number().randomDouble(1, 0, 10));
//            film.setGenres(List.of(genres.get(new Random().nextInt(genres.size()))));
//            film.setActors(List.of(actors.get(new Random().nextInt(actors.size()))));
//            film.setDirectors(List.of(directors.get(new Random().nextInt(directors.size()))));
//            filmRepository.save(film);
//            logger.info("Фильм создан: {}", film);
//
//            // Создание отзывов
//            for (int j = 0; j < 5; j++) {
//                Reviews review = new Reviews();
//                review.setComment(faker.lorem().sentence());
//                review.setEstimation(faker.number().numberBetween(1, 10));
//                review.setDateTime(LocalDateTime.now());
//                review.setUser(userRepository.findAll().get(new Random().nextInt(userRepository.findAll().size())));
//                review.setFilm(film);
//                reviewRepository.save(review);
//                logger.info("Отзыв создан: {}", review);
//            }
//
//            // Создание билетов
//            for (int j = 0; j < 10; j++) {
//                Ticket ticket = new Ticket();
//                ticket.setPrice((float) faker.number().randomDouble(2, 10, 100));
//                ticket.setPurchaseDate(LocalDateTime.now());
//                ticket.setUser(userRepository.findAll().get(new Random().nextInt(userRepository.findAll().size())));
//                ticket.setFilm(film);
//                ticketRepository.save(ticket);
//                logger.info("Билет создан: {}", ticket);
//            }
//        }
//
//        logger.info("Инициализация данных завершена.");
//    }
//}
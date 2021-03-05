package com.library.mslibrary;

import com.library.mslibrary.config.ApplicationPropertiesConfig;
import com.library.mslibrary.entities.Book;
import com.library.mslibrary.entities.BookLoan;
import com.library.mslibrary.entities.BookReservation;
import com.library.mslibrary.entities.User;
import com.library.mslibrary.security.WebSecurityConfig;
import com.library.mslibrary.service.BookLoanService;
import com.library.mslibrary.service.BookReservationService;
import com.library.mslibrary.service.BookService;
import com.library.mslibrary.service.UserService;
import com.library.mslibrary.utils.DateTools;
import com.library.mslibrary.utils.RandomTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication(scanBasePackages="com.library")
@EnableConfigurationProperties
@EnableDiscoveryClient
@EnableFeignClients("com.library.mslibrary")
public class MsLibraryApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(MsLibraryApplication.class);

	@Autowired
	private WebSecurityConfig webSecurityConfig;

	@Autowired
	private ApplicationPropertiesConfig appConfig;

	@Autowired
	private UserService userService;

	@Autowired
	private BookService bookService;

	@Autowired
	private BookLoanService bookLoanService;

	@Autowired
	private BookReservationService bookReservationService;

	public static void main(String[] args) {
		SpringApplication.run(MsLibraryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		boolean isBddInit = false;

		/* Initialize BDD with sample test users if empty (on first launch only) */
		LOGGER.info("Recherche de l'existance des utilisateurs en BDD");

		if (CollectionUtils.isEmpty(userService.findAll())) {
			LOGGER.info("Création d'un jeu de données utilisateur de test (table 'user')");
			isBddInit=true;

			List<User> userList = Arrays.asList(
					new User(
							"email@user1.fr",
							"user1_lastName",
							"user1_firstName",
							webSecurityConfig.passwordEncoder().encode("passwordUser1"),
							"user"
					),
					new User(
							"email@user2.fr",
							"user2_lastName",
							"user2_firstName",
							webSecurityConfig.passwordEncoder().encode("passwordUser2"),
							"user"
					),
					new User(
							"email@user3.fr",
							"user3_lastName",
							"user3_firstName",
							webSecurityConfig.passwordEncoder().encode("passwordUser3"),
							"user"
					),
					new User(
							"email@user4.fr",
							"user4_lastName",
							"user4_firstName",
							webSecurityConfig.passwordEncoder().encode("passwordUser4"),
							"user"
					),
					new User(
							"email@admin1.fr",
							"admin1_lastName",
							"admin1_firstName",
							webSecurityConfig.passwordEncoder().encode("passwordAdmin1"),
							"admin"
					),
					new User(
							"email@admin2.fr",
							"admin2_lastName",
							"admin2_firstName",
							webSecurityConfig.passwordEncoder().encode("passwordAdmin2"),
							"admin"
					)
			);

			userService.saveAll(userList);
			LOGGER.info("Ajout de {} Utilisateurs", userList.size());
		}

		if (isBddInit) {

			/* Initializing BDD with sample test books and loans */
			String loremIpsum = "Vi simulationem difficillimum aliis et placuerat placuerat simulationem ponderibus " +
					"simulationem tamquam placuerat nimis obstaculo nimis confidentia auxilio commentis qua nimis." +
					"<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer sed velit eget leo pharetra" +
					" pretium. Duis imperdiet a turpis vitae mattis. Praesent molestie at turpis sit amet lacinia." +
					" Cras sed dui at massa ultricies fringilla quis sit amet lectus.</p>" +
					"<p>Donec quis ultrices risus. Phasellus lobortis nec dolor vel porttitor. Nam eu mauris tortor." +
					" Integer pretium vel dui et luctus. Integer sed odio vestibulum mi pellentesque aliquam." +
					" Donec lacinia cursus arcu sed lobortis. Mauris efficitur tellus in nulla posuere viverra.</p>";
			if (CollectionUtils.isEmpty(bookService.findAll())) {
				LOGGER.info("Création d'un jeu de données de livres en BDD");
				List<Book> bookList = Arrays.asList(
						new Book(
								"titre1 Lorem ipsum",
								"desc1 "+loremIpsum,
								"author1",
								"editor1",
								"collection1",
								"isbn1",
								DateTools.addDays(new Date(), - RandomTools.randomNum(100,999))
						),
						new Book(
								"titre2 Lorem ipsum",
								"desc2 "+loremIpsum,
								"author2",
								"editor2",
								"collection2",
								"isbn2",
								DateTools.addDays(new Date(), - RandomTools.randomNum(100,999))
						),
						new Book(
								"titre3 Lorem ipsum",
								"desc3 "+loremIpsum,
								"author3",
								"editor3",
								"collection3",
								"isbn3",
								DateTools.addDays(new Date(), - RandomTools.randomNum(100,999))
						),
						new Book(
								"titre4 Lorem ipsum",
								"desc4 "+loremIpsum,
								"author4",
								"editor4",
								"collection4",
								"isbn4",
								DateTools.addDays(new Date(), - RandomTools.randomNum(100,999))
						),
						new Book(
								"titre5 Lorem ipsum",
								"desc5 "+loremIpsum,
								"author5",
								"editor5",
								"collection5",
								"isbn5",
								DateTools.addDays(new Date(), - RandomTools.randomNum(100,999))
						),
						new Book(
								"titre6 Lorem ipsum",
								"desc6 "+loremIpsum,
								"author6",
								"editor6",
								"collection6",
								"isbn6",
								DateTools.addDays(new Date(), - RandomTools.randomNum(100,999))
						),
						new Book(
								"titre7 Lorem ipsum",
								"desc7 "+loremIpsum,
								"author7",
								"editor7",
								"collection7",
								"isbn7",
								DateTools.addDays(new Date(), - RandomTools.randomNum(100,999))
						),
						new Book(
								"titre8 Lorem ipsum",
								"desc8 "+loremIpsum,
								"author8",
								"editor8",
								"collection8",
								"isbn8",
								DateTools.addDays(new Date(), - RandomTools.randomNum(100,999))
						),
						new Book(
								"titre9 Lorem ipsum",
								"desc9 "+loremIpsum,
								"author9",
								"editor9",
								"collection9",
								"isbn9",
								DateTools.addDays(new Date(), - RandomTools.randomNum(100,999))
						),
						new Book(
								"titre10 Lorem ipsum",
								"desc10 "+loremIpsum,
								"author10",
								"editor10",
								"collection10",
								"isbn10",
								DateTools.addDays(new Date(), - RandomTools.randomNum(100,999))
						)
				);

				int i=1;
				for (Book book : bookList) {
					if (i>10) {i=RandomTools.randomNum(1,10);}
					book.setOnline(true);
					book.setStock(RandomTools.randomNum(1,5));
					book.setNbCopy(RandomTools.randomNum(1,5));
					book.setReservationAvailable(true);
					if (book.getStock()>book.getNbCopy()) {
						book.setStock(book.getNbCopy());
					}
					if ((book.getStock() < 1)) {
						book.setLoanAvailable(false);
					} else {
						book.setLoanAvailable(true);
					}
					book.setImgPathThAttribute("/img/book/book"+i+"_cover_400x400.jpg");
					i+=1;
				}

				bookService.saveAll(bookList);
				LOGGER.info("Ajout de {} Livres", bookList.size());
			}

			if (CollectionUtils.isEmpty(bookLoanService.findAll())) {
				LOGGER.info("Création d'un jeu de données d'emprunt de livre en BDD");
				List<BookLoan> bookLoanList = Arrays.asList(
						new BookLoan(
								userService.findUserByEmail("email@user1.fr"),
								bookService.findBookById(1L),
								appConfig.getBookLoanDuration()
						),
						new BookLoan(
								userService.findUserByEmail("email@user2.fr"),
								bookService.findBookById(2L),
								appConfig.getBookLoanDuration()
						),
						new BookLoan(
								userService.findUserByEmail("email@user1.fr"),
								bookService.findBookById(2L),
								appConfig.getBookLoanDuration()
						),
						new BookLoan(
								userService.findUserByEmail("email@user2.fr"),
								bookService.findBookById(1L),
								appConfig.getBookLoanDuration()
						),
						new BookLoan(
								userService.findUserByEmail("email@user3.fr"),
								bookService.findBookById(3L),
								appConfig.getBookLoanDuration()
						),
						new BookLoan(
								userService.findUserByEmail("email@user4.fr"),
								bookService.findBookById(4L),
								appConfig.getBookLoanDuration()
						)
				);

				for (BookLoan bookLoan : bookLoanList) {
					bookLoan.setStartLoan(DateTools.addDays(new Date(), -RandomTools.randomNum(1, 5)));
					bookLoan.setEndLoan(DateTools.addDays(bookLoan.getStartLoan(), appConfig.getBookLoanDuration()));
				}
				bookLoanService.saveAll(bookLoanList);
				LOGGER.info("Ajout de {} prêts de livres", bookLoanList.size());

			}

			if (CollectionUtils.isEmpty(bookReservationService.findAll())) {
				LOGGER.info("Création d'un jeu de données de réservation de livre en BDD");
				List<BookReservation> bookReservationList = Arrays.asList(
						new BookReservation(
								userService.findUserByEmail("email@user1.fr"),
								bookService.findBookById(1L)
						),
						new BookReservation(
								userService.findUserByEmail("email@user2.fr"),
								bookService.findBookById(2L)
						),
						new BookReservation(
								userService.findUserByEmail("email@user3.fr"),
								bookService.findBookById(1L)
						),
						new BookReservation(
								userService.findUserByEmail("email@user4.fr"),
								bookService.findBookById(2L)
						),
						new BookReservation(
								userService.findUserByEmail("email@user4.fr"),
								bookService.findBookById(1L)
						),
						new BookReservation(
								userService.findUserByEmail("email@user1.fr"),
								bookService.findBookById(3L)
						)
				);

				for (BookReservation bookReservation : bookReservationList) {
					bookReservation.setCreationDate(DateTools.addDays(new Date(), - RandomTools.randomNum(1,5)));
				}
				bookReservationService.saveAll(bookReservationList);
				LOGGER.info("Ajout de {} réservations de livres", bookReservationList.size());

			}
		}

		if (!isBddInit) {
			LOGGER.info("Des utilisateurs existent déjà en BDD - FIN de création du jeu de données");
		} else {
			LOGGER.info("FIN de création du jeu de données");
		}
	}

}

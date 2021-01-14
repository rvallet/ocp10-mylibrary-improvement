INSERT INTO library_bdd.book (author,collection,description,editor,img_path_th_attribute,is_loan_available,is_online,isbn,release_date,short_description,stock,title) VALUES 
('author1','collection1','description1','editor1',NULL,1,1,'isbn1','2020-05-04 10:15:01','shortDescription1',4,'title1')
,('author2','collection2','description2','editor2',NULL,1,1,'isbn2','2018-12-25 11:15:01','shortDescription2',2,'title2')
,('author3','collection3','description3','editor3',NULL,1,1,'isbn3','2019-01-25 11:15:01','shortDescription3',4,'title3')
,('author4','collection4','description4','editor4',NULL,1,1,'isbn4','2019-09-05 10:15:01','shortDescription4',2,'title4')
,('author5','collection5','description5','editor5',NULL,1,1,'isbn5','2018-04-01 10:15:01','shortDescription5',0,'title5')
,('author6','collection6','description6','editor6',NULL,1,1,'isbn6','2020-05-28 10:15:01','shortDescription6',5,'title6')
,('author7','collection7','description7','editor7',NULL,1,1,'isbn7','2020-03-01 11:15:01','shortDescription7',5,'title7')
,('author8','collection8','description8','editor8',NULL,1,1,'isbn8','2018-07-26 10:15:01','shortDescription8',1,'title8')
,('author9','collection9','description9','editor9',NULL,1,1,'isbn9','2019-07-13 10:15:01','shortDescription9',2,'title9')
,('author10','collection10','description10','editor10',NULL,1,1,'isbn10','2018-02-03 11:15:01','shortDescription10',2,'title10')
;

INSERT INTO library_bdd.`user` (address,creation_date,email,first_name,last_name,password,reset_token,`role`) VALUES 
(NULL,'2020-10-12 10:15:00','email@user1.fr','user1_firstName','user1_lastName','$2a$10$Oc7iszHiUUgscXXxgFX0COjIOYKyqI8Jhn2t2.jztLUD3gbOAHwpK',NULL,'user')
,(NULL,'2020-10-12 10:15:01','email@user2.fr','user2_firstName','user2_lastName','$2a$10$jfDCjCLh5k3.gfhPIy2NC.bU2adczcich/6PifGuOAWItgeqGupmO',NULL,'user')
,(NULL,'2020-10-12 10:15:01','email@admin1.fr','admin1_firstName','admin1_lastName','$2a$10$ZL9lc4ZU/uAiPmle.LD94ehrdnD3Jlz44yiMretxMBkkQN9qkIqca',NULL,'admin')
,(NULL,'2020-10-12 10:15:01','email@admin2.fr','admin2_firstName','admin2_lastName','$2a$10$9YkfyyUlbEevvSgTjZgeFexQ3N1aqoLCcfipb6zE9mh6tEKQFDsiK',NULL,'admin')
,(NULL,'2020-10-13 11:13:17','remy.vallet-EXPL@worldline.com','Rémy','VALLET','$2a$10$J8ERmb6meR30OCmHEnkaMuTpeAqPFbIR3DsadIUKq.XCdLkLlTdkm',NULL,'admin')
;

INSERT INTO library_bdd.book_loan_email_reminder (book_id,book_loan_id,book_title,end_loan,firstname,is_email_sent,lastname,sending_email_date,user_email,user_id) VALUES 
(2,2,'title2','2020-10-12 11:15:01','user2_firstName',1,'user2_lastName','2020-10-13 11:45:33','email@user2.fr',2)
,(1,1,'title1','2020-10-12 11:15:01','user1_firstName',1,'user1_lastName','2020-10-13 11:45:33','email@user1.fr',1)
,(2,2,'title2','2020-10-12 11:15:01','user2_firstName',1,'user2_lastName','2020-10-13 11:20:25','email@user2.fr',2)
;

INSERT INTO library_bdd.book_loan (end_loan,is_loan_extended,loan_status,return_loan,start_loan,id_book,id_user) VALUES 
('2020-10-12 11:15:01',1,'Prolongé',NULL,'2020-10-12 10:15:01',1,1)
,('2020-10-12 11:15:01',0,'En cours',NULL,'2020-10-12 10:15:01',2,2)
,('2020-10-11 11:15:01',0,'Terminé','2020-10-11 11:15:01','2020-10-12 10:15:01',2,1)
,('2020-10-12 11:15:01',0,'Terminé','2020-10-13 11:14:47','2020-10-12 10:15:01',1,2)
,('2020-10-27 12:15:25',0,'En cours',NULL,'2020-10-13 11:15:25',2,5)
;
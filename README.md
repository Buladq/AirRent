# AirRent
Стек: Spring Boot Core, MVC, Security, Data JPA, MySQL.
Автозаполнение данных благодаря data.sql.
Имеется возможность отправки сообщения с новым паролем на почту и возможность скачать билет в формате pdf.

Полноценный авиасервис с ролями пилота,админа и юзера и возможностью расчета расстояния между городов по координатам (покупка билета и аренда рейса).
Для работы вам нужно настроить [этот файл](https://github.com/Buladq/AirRent/blob/master/src/main/resources/application.properties).

После запуска приложения база данных сама заполнится нужными данными для базового просмотра веб-сервиса и удалится после выключение.

Если у вас будет желание поработать с базами данных и не удалять данные то поменяйте spring.jpa.hibernate.ddl-auto=create-drop на spring.jpa.hibernate.ddl-auto=update, запустите приложение,
удалите эти строчки spring.sql.init.mode=always
spring.sql.init.data-locations=classpath:db/data.sql
spring.jpa.defer-datasource-initialization=true
и удалите директорию db.

Логин и пароль для админа admin777 и для пилота pilottest1.

![image](https://github.com/Buladq/AirRent/assets/87196965/10f5ba61-4477-4bf7-9f34-fa9e003eab2e)
![image](https://github.com/Buladq/AirRent/assets/87196965/12b242cf-5154-4833-9d54-09f68509d088)
![image](https://github.com/Buladq/AirRent/assets/87196965/1e45b051-21e2-4547-96b0-e3d2cc6050a6)
![image](https://github.com/Buladq/AirRent/assets/87196965/435ebd3d-f83c-4567-996e-2be44721e55b)
![image](https://github.com/Buladq/AirRent/assets/87196965/a3474425-2d15-4043-8ff1-4e048c995364)
![image](https://github.com/Buladq/AirRent/assets/87196965/f2c89540-cc54-4586-bd6b-cad5bdd5d599)
![image](https://github.com/Buladq/AirRent/assets/87196965/dbf6e5cb-ca36-491e-ac41-99f2bdd0eedc)
![image](https://github.com/Buladq/AirRent/assets/87196965/654115d4-c66e-4668-94cb-b6fa6c555721)


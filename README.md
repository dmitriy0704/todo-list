# Проект Todo List 

[Ссылка на SWAGGER](http://localhost:8080/swagger-ui/index.html)

Проект запускается в среде разработки, БД поднимается автоматически в контейнере.
Т.к. регистрация задачей не стояла, а только авторизация и аутентификация, 
в базе создается 4 пользователя: администратор и 3 юзера.
Логины/пароли:
- admin@gmail.com/password1
- user1@gmail.com/password1
- user2@gmail.com/password1
- user3@gmail.com/password1

Для создания задач администратору необходимо авторизоваться - отправить
логин/пароль постом и получить токен, подставить его в заголовок запроса
работы с задачами администратором или пользователем. Для этого отлично подходит 
Postman.

В параметрах проекта свойство `sql.init.mode: allways` обеспечивает вставку 
данных в БД. Данные вставляется один раз и при последующих запусках с этим 
свойством приложение не запускается отвечая что такая таблица уже есть. Чтобы 
это исправить необходимо установить значение свойства `sql.init.mode: never`.


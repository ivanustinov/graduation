### curl samples (application deployed in application context `graduation`).

#####  Get all restaurants
`curl -s http://localhost:8080/graduation/restaurants --user admin@gmail.com:admin`

#####  Get all restaurants with menues dishes
`curl -s http://localhost:8080/graduation/restaurants/menues --user admin@gmail.com:admin`

##### Get all restaurants with votes
`curl -s http://localhost:8080/graduation/restaurants/votes --user admin@gmail.com:admin`

##### Get restaurants with menues and votes
`curl -s http://localhost:8080/graduation/restaurants/menues-and-votes --user admin@gmail.com:admin`

##### Get restaurant with id 100003
`curl -s http://localhost:8080/graduation/restaurants/100003 --user admin@gmail.com:admin`

##### Get restaurant with id 100003 with menues
`curl -s http://localhost:8080/graduation/restaurants/100003/menues --user admin@gmail.com:admin`

##### Get restaurant with id 100003 with votes
`curl -s http://localhost:8080/graduation/restaurants/100003/votes --user admin@gmail.com:admin`

##### Get restaurant with id 100003 with menues and votes
`curl -s http://localhost:8080/graduation/restaurants/100003/menues-and-votes --user admin@gmail.com:admin`

##### Get restaurants that have menue for date 2019-09-11
`curl -s http://localhost:8080/graduation/restaurants/haveMenu?date=2019-09-11 --user admin@gmail.com:admin`

##### Get restaurants with menue by date 2019-09-11
`curl -s http://localhost:8080/graduation/restaurants/menues?date=2019-09-11 --user user1@yandex.ru:password`

##### Get restaurants with votes by date 2019-09-11
`curl -s http://localhost:8080/graduation/restaurants/votes?date=2019-09-11 --user admin@gmail.com:admin`

##### Get restaurant with id 100003 with menue by date 2019-09-11
`curl -s http://localhost:8080/graduation/restaurants/100003/menues?date=2019-09-11 --user admin@gmail.com:admin`

##### Get restaurant with id 100003 with votes by date 2019-09-11
`curl -s http://localhost:8080/graduation/restaurants/100003/votes?date=2019-09-11 --user admin@gmail.com:admin`

##### Get restaurant with id 100003 with menue and votes by date 2019-09-11
`curl -s http://localhost:8080/graduation/restaurants/100003/menues-and-votes?date=2019-09-11 --user admin@gmail.com:admin`

##### Get restaurantVotesCountTo with id 100003 by date 2019-09-11
`curl -s http://localhost:8080/graduation/restaurants/100003/votes-count?date=2019-09-11 --user admin@gmail.com:admin`

##### Get restaurantsVoteCountTo by date 2019-09-11
`curl -s http://localhost:8080/graduation/restaurants/votes-count?date=2019-09-11 --user user1@yandex.ru:password`

##### Create new restaurant
`curl -s -X POST -d '{"name":"New restaurant", "address":"New address"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/restaurants --user admin@gmail.com:admin`

##### Update restaurant with id 100003
`curl -s -X PUT -d '{"id":"100003", "name":"Updated restaurant", "address":"Updated address"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/restaurants/100003 --user admin@gmail.com:admin`

##### Delete restaurant with id 100003
`curl -s -X DELETE http://localhost:8080/graduation/restaurants/100003 --user admin@gmail.com:admin`
_________________________

#####  Get all votes
`curl -s http://localhost:8080/graduation/votes --user admin@gmail.com:admin`

##### Get vote with id 100027
`curl -s http://localhost:8080/graduation/votes/100027 --user admin@gmail.com:admin`

##### Get profile vote by date 2019-09-11
`curl -s http://localhost:8080/graduation/profile/vote?date=2019-09-11 --user user1@yandex.ru:password`

##### Get vote by user id and date 2019-09-11
`curl -s http://localhost:8080/graduation/users/100000/vote?date=2019-09-11 --user admin@gmail.com:admin`

##### Profile vote for restaurant
`curl -s -X PUT http://localhost:8080/graduation/profile/vote?restaurantId=100003 --user user1@yandex.ru:password`

##### Delete vote with id 100027
`curl -s -X DELETE http://localhost:8080/graduation/votes/100027 --user admin@gmail.com:admin`
_________________________

##### Get all menues
`curl -s http://localhost:8080/graduation/menues --user admin@gmail.com:admin`

##### Get menue with id 100016
`curl -s http://localhost:8080/graduation/menues/100016 --user admin@gmail.com:admin`

##### Get menues by date 2019-09-11
`curl -s http://localhost:8080/graduation/menues?date=2019-09-11 --user admin@gmail.com:admin`

##### Delete menue with id 100016
`curl -s -X DELETE http://localhost:8080/graduation/menues/100016 --user admin@gmail.com:admin`

##### Create new menue
`curl -s -X POST -d '{"date":"2019-09-12", "restaurantId":"100004", "dishId":"100010"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/menues --user admin@gmail.com:admin`

##### Update menue with id 100016
`curl -s -X PUT -d '{"id":"100016", "date":"2019-09-11", "restaurantId":"100003", "dishId":"100008"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/menues/100016 --user admin@gmail.com:admin`

_________________________

##### Get all dishes
`curl -s http://localhost:8080/graduation/dishes --user admin@gmail.com:admin`

##### Get dish with id 100008
`curl -s http://localhost:8080/graduation/dishes/100008 --user admin@gmail.com:admin`

##### Delete dish with id 100008
`curl -s -X DELETE http://localhost:8080/graduation/dishes/100008 --user admin@gmail.com:admin`

##### Create new dish
`curl -s -X POST -d '{"name":"New dish",	"price":"15050"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/dishes --user admin@gmail.com:admin`

##### Update dish with id 100008
`curl -s -X PUT -d '{"id":"100008",	"name":"Updated dish", "price":"8000"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/dishes/100008 --user admin@gmail.com:admin`

_________________________

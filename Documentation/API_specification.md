# API Specification
## Rooms
### Get Room List
- **URL**: `/rooms`
- **Method**: GET
#### Parameters

| Name     | Type     | Description                                                                           | Example               |
| :------- | :------- | :------------------------------------------------------------------------------------ | :-------------------- |
| `filter` | `string` | Only rooms where the name contains the given filter are returned. Not case sensitive. | `?filter=suite`       |
| `sort`   | `string` | The room's attribute by which the returned list should be sorted.                     | `?sort=pricePerNight` |
| `page`   | `number` | MANDATORY! Current page that should be loaded.                                        | `?page=1` |
| `size`   | `number` | Number of elements returned with the current page load. Default = 5                   | `?size=5` |
#### Responses

| Code                        | Description                                           | Content                   |
| :-------------------------- | :---------------------------------------------------- | :------------------------ |
| `200 OK`                    | Returns list of rooms                                 | [List of Rooms](#list-of-rooms) |
| `500 Internal Server Error` | Something went wrong, please contact our service-desk | [Error](#errordto)           |
##### Success
```
{
	"rooms": [
		{
			"id": Guid,
			"name": string,
			"description": string,
			"pricePerNight": double,
            "extras": string[],
            "imagePaths": string[]
		},
		...
	],
    "page": {
        "size": number,
        "totalElements": number,
        "totalPages": number,
        "number": number
    }
}
```
##### Error
```
{
	"errorMessage": string
}
```

---
### Check Room Availability
- **URL**: `/rooms/:id/availability`
- **Method**: GET
#### Parameters

| Name     | Type     | Description                                            | Example            |
| :------- | :------- | :----------------------------------------------------- | :----------------- |
| `from`   | `date`   | May not lie in the past and has to be set before "to". | `?from=2026-05-02` |
| `to`     | `date`   | May not lie in the past and has to be set after "from". | `?to=2026-05-03`  |
#### Responses

| Code                        | Description                                           | Content                   |
| :-------------------------- | :---------------------------------------------------- | :------------------------ |
| `200 OK`                    | Returns if the room is available for the selected timespan. | [Availability](#availability) |
| `404 Not Found`             | No room with the given id found. | [Error](#errordto)           |
| `500 Internal Server Error` | Something went wrong, please contact our service-desk | [Error](#errordto)           |
##### Success
```
{
    "available": boolean
}
```
##### Error
```
{
	"errorMessage": string
}
```

---
### Get Room Details
- **URL**: `/rooms/:id`
- **Method**: GET
#### Responses
| Code                        | Description                                           | Content            |
| :-------------------------- | :---------------------------------------------------- | :----------------- |
| `200 OK`                    | Success                                               | [Room](#room) |
| `404 Not Found`             | The requested room id does not exist                  | [Error](#errordto)  |
| `500 Internal Server Error` | Something went wrong, please contact our service-desk | [Error](#errordto)  |
##### Success
```
{
	"id": Guid,
	"name": string,
	"description": string,
	"pricePerNight": double,
    "extras": string[],
    "imagePaths": string[]
}
```
##### Error
```
{
	"errorMessage": string
}
```
### Get Room Bookings
- **URL**: `/rooms/:id/bookings`
- **Method**: GET
#### Description
Fetches all bookings for a given room id.
#### Parameters

| Name     | Type     | Description                                                                           | Example               |
| :------- | :------- | :------------------------------------------------------------------------------------ | :-------------------- |
| `page`   | `number` | Current page that should be loaded. If omitted, all data is returned.                     | `?page=1` |
| `size`   | `number` | Number of elements returned with the current page load. If omitted, all data is returned. | `?size=5` |

#### Responses
| Code                        | Description                                           | Content                        |
| :-------------------------- | :---------------------------------------------------- | :----------------------------- |
| `200 OK`                    | Success                                               | [List of Bookings](#list-of-bookings) |
| `404 Not Found`             | The requested room id does not exist                  | [Error](#errordto)              |
| `500 Internal Server Error` | Something went wrong, please contact our service-desk | [Error](#errordto)              |
##### Success
```
{
    "bookings": [
        {
            "id": Guid,
            "userId": Guid,
            "roomId": Guid,
            "from": DateTime,
            "to": DateTime,
            "breakfast": boolean,
            "duration": number,
            "totalPrice": number
        },
        ...
    ],
    "page": {
        "size": number,
        "totalElements": number,
        "totalPages": number,
        "number": number
    }
}
```
##### Error

```
{
	"errorMessage": string
}
```
## Bookings
### Book Room
- **URL**: `/bookings
- **Method**: POST
#### Description
Creates a booking for a user and a room for a selected time range. DateTime is expected in the following format: 'YYYY-MM-DD'. Either a userId or guest element has to be transmitted with the body. If a guest element is provided, either the user with the given email is returned or a new user is created from it.
#### Body
```
{
	"roomId": Guid,
	"from": DateTime,
	"to": DateTime,
	"breakfast": boolean,
    "userId": Guid | null,
    "guest": {
        "firstName": string,
        "lastName": string,
        "email": string
    } | null
}
```
#### Responses
| Code                        | Description                                                                                                        | Content               |
| :-------------------------- | :----------------------------------------------------------------------------------------------------------------- | :-------------------- |
| `201 Created`               | Success                                                                                                            | [Booking](#booking) |
| `400 Bad Request`           | The sent request body was malformed. This includes illegal roomId, if "to" is set before "from", or any of them lies in the past. Also if neither a "guest" nor a "userId" is given.| [Error](#errordto)     |
| `409 Conflict`              | The requested room is no longer available for the selected time range. Maybe the process of booking took too long. | [Error](#errordto)     |
| `500 Internal Server Error` | Something went wrong, please contact our service-desk                                                              | [Error](#errordto)     |
##### Success
```
{
	"id": Guid,
	"userId": Guid,
	"roomId": Guid,
	"from": DateTime,
	"to": DateTime,
	"breakfast": boolean,
	"duration": number,
	"totalPrice": number
}
```
##### Error
```
{
	"errorMessage": string
}
```
### Update Booking
- **URL**: `/bookings/:id
- **Method**: PUT
#### Description
Updates a booking. Idempotent. DateTime is expected in the following format: 'YYYY-MM-DD'.  Either a userId or guest element has to be transmitted with the body. If a guest element is provided, either the user with the given email is returned or a new user is created from it.
#### Body
```
{
	"roomId": Guid,
	"from": DateTime,
	"to": DateTime,
	"breakfast": boolean,
    "userId": Guid | null,
    "guest": {
        "firstName": string,
        "lastName": string,
        "email": string
    } | null
}
```
#### Responses
| Code                        | Description                                                                                                  | Content               |
| :-------------------------- | :----------------------------------------------------------------------------------------------------------- | :-------------------- |
| `200 OK`                    | Success                                                                                                      | [Booking](#booking) |
| `400 Bad Request`           | The sent request body was malformed. This includes illegal roomId, if "to" is set before "from", or any of them lies in the past. Also if neither a "guest" nor a "userId" is given. | [Error](#errordto)     |
| `404 Not Found`             | The requested booking id does not exist                                                                      | [Error](#errordto)     |
| `409 Conflict`              | The requested room is not available for the selected time range. Maybe the process of booking took too long. | [Error](#errordto)     |
| `500 Internal Server Error` | Something went wrong, please contact our service-desk                                                        | [Error](#errordto)     |
##### Success
```
{
	"id": Guid,
	"userId": Guid,
	"roomId": Guid,
	"from": DateTime,
	"to": DateTime,
	"breakfast": boolean,
	"duration": number,
	"totalPrice": number
}
```
##### Error
```
{
	"errorMessage": string
}
```
### Cancel Booking
- **URL**: `/bookings/:id
- **Method**: DELETE
#### Description
Cancels and deletes the given booking.
#### Responses
| Code                        | Description                                           | Content           |
| :-------------------------- | :---------------------------------------------------- | :---------------- |
| `204 No Content`            | Success                                               |                   |
| `404 Not Found`             | The requested booking id does not exist               | [Error](#errordto) |
| `500 Internal Server Error` | Something went wrong, please contact our service-desk | [Error](#errordto) |
##### Error
```
{
	"errorMessage": string
}
```
### Get Bookings
- **URL**: `/bookings`
- **Method**: GET
#### Description
Fetches a list of all bookings.
#### Parameters

| Name     | Type     | Description                                                                           | Example               |
| :------- | :------- | :------------------------------------------------------------------------------------ | :-------------------- |
| `page`   | `number` | Current page that should be loaded. If omitted, all data is returned.                     | `?page=1` |
| `size`   | `number` | Number of elements returned with the current page load. If omitted, all data is returned. | `?size=5` |

#### Responses
| Code                        | Description                                           | Content                        |
| :-------------------------- | :---------------------------------------------------- | :----------------------------- |
| `200 OK`                    | Success                                               | [List of Bookings](#list-of-bookings) |
| `500 Internal Server Error` | Something went wrong, please contact our service-desk | [Error](#errordto)              |
##### Success
```
{
    "bookings": [
        {
            "id": Guid,
            "userId": Guid,
            "roomId": Guid,
            "from": DateTime,
            "to": DateTime,
            "breakfast": boolean,
            "duration": number,
            "totalPrice": number
        },
        ...
    ],
    "page": {
        "size": number,
        "totalElements": number,
        "totalPages": number,
        "number": number
    }
}
```
##### Error
```
{
	"errorMessage": string
}
```

## Users
### Create User
- **URL**: `/users
- **Method**: POST
#### Description
Creates a new user.
#### Body
```
{
	"userMail": string,
	"firstName": string,
	"lastName": string
}
```
#### Responses
| Code                        | Description                                                                                                  | Content            |
| :-------------------------- | :----------------------------------------------------------------------------------------------------------- | :----------------- |
| `201 Created`               | Success                                                                                                      | [User](#user) |
| `400 Bad Request`           | The sent request body was malformed or mail is already registered. Also if mail is not a valid mail address. | [Error](#errordto)  |
| `500 Internal Server Error` | Something went wrong, please contact our service-desk                                                        | [Error](#errordto)  |
##### Success
```
{
	"id": Guid,
	"userMail": string,
	"firstName": string,
	"lastName": string
}
```
##### Error
```
{
	"errorMessage": string
}
```
### Get users
- **URL**: `/users
- **Method**: GET
#### Description
Fetches a list of all users.
#### Parameters

| Name     | Type     | Description                                                                           | Example               |
| :------- | :------- | :------------------------------------------------------------------------------------ | :-------------------- |
| `page`   | `number` | Current page that should be loaded. If omitted, all data is returned.                     | `?page=1` |
| `size`   | `number` | Number of elements returned with the current page load. If omitted, all data is returned. | `?size=5` |

#### Responses
| Code                        | Description                                                        | Content                     |
| :-------------------------- | :----------------------------------------------------------------- | :-------------------------- |
| `200 OK`                    | Success                                                            | [List of Users](#list-of-users) |
| `500 Internal Server Error` | Something went wrong, please contact our service-desk              | [Error](#errordto)          |
##### Success
```
{
    "users": [
        {
            "id": Guid,
            "userMail": string,
            "firstName": string,
            "lastName": string
        },
        ...
    ],
    "page": {
        "size": number,
        "totalElements": number,
        "totalPages": number,
        "number": number
    }
}
```
##### Error
```
{
	"errorMessage": string
}
```
### Get user details
- **URL**: `/users/:id
- **Method**: GET
#### Description
Fetches a specific user by his id.
#### Responses
| Code                        | Description                                           | Content                     |
| :-------------------------- | :---------------------------------------------------- | :-------------------------- |
| `200 OK`                    | Success                                               | [User](#user) |
| `404 Not Found`             | No user with the given id found                       | [Error](#errordto)          |
| `500 Internal Server Error` | Something went wrong, please contact our service-desk | [Error](#errordto)          |
##### Success
```
{
    "id": Guid,
    "userMail": string,
    "firstName": string,
    "lastName": string
}
```
##### Error
```
{
	"errorMessage": string
}
```
### Update User
- **URL**: `/users/:id
- **Method**: PUT
#### Description
Updates a user. Idempotent.
#### Body
```
{
	"userMail": string,
	"firstName": string,
	"lastName": string
}
```
#### Responses
| Code                        | Description                                           | Content             |
| :-------------------------- | :---------------------------------------------------- | :------------------ |
| `200 OK`                    | Success                                               | [User](#user) |
| `400 Bad Request`           | The sent request body was malformed or mail is already registered. Also if mail is not a valid mail address. | [Error](#errordto)  |
| `404 Not Found`             | The requested user id does not exist                  | [Error](#errordto)  |
| `500 Internal Server Error` | Something went wrong, please contact our service-desk | [Error](#errordto)  |
##### Success
```
{
	"id": Guid,
	"userMail": string,
	"firstName": string,
	"lastName": string
}
```
##### Error
```
{
	"errorMessage": string
}
```
### Delete User
- **URL**: `/users/:id
- **Method**: DELETE
#### Description
Deletes a user and all of his bookings cascading.
#### Responses
| Code                        | Description                                           | Content            |
| :-------------------------- | :---------------------------------------------------- | :----------------- |
| `204 No Content`            | Success                                               |                    |
| `404 Not Found`             | The requested user id does not exist                  | [Error](#errordto) |
| `500 Internal Server Error` | Something went wrong, please contact our service-desk | [Error](#errordto) |
##### Error
```
{
	"errorMessage": string
}
```
### Get User Bookings
- **URL**: `/users/:id/bookings`
- **Method**: GET
#### Description
Fetches all bookings for a given user id.
#### Parameters

| Name     | Type     | Description                                                                           | Example               |
| :------- | :------- | :------------------------------------------------------------------------------------ | :-------------------- |
| `page`   | `number` | Current page that should be loaded. If omitted, all data is returned.                     | `?page=1` |
| `size`   | `number` | Number of elements returned with the current page load. If omitted, all data is returned. | `?size=5` |

#### Responses
| Code                        | Description                                           | Content                        |
| :-------------------------- | :---------------------------------------------------- | :----------------------------- |
| `200 OK`                    | Success                                               | [List of Bookings](#list-of-bookings) |
| `404 Not Found`             | The requested room id does not exist                  | [Error](#errordto)              |
| `500 Internal Server Error` | Something went wrong, please contact our service-desk | [Error](#errordto)              |
##### Success
```
{
    "bookings": [
        {
            "id": Guid,
            "userId": Guid,
            "roomId": Guid,
            "from": DateTime,
            "to": DateTime,
            "breakfast": boolean,
            "duration": number,
            "totalPrice": number
        },
        ...
    ],
    "page": {
        "size": number,
        "totalElements": number,
        "totalPages": number,
        "number": number
    }
}
```
##### Error

```
{
	"errorMessage": string
}
```
# DTOs
### Room
```
{
	"id": Guid,
	"name": string,
	"description": string,
	"pricePerNight": double,
    "extras": string[],
    "imagePaths": string[]
}
```
### List of Rooms
```
{
	"rooms": [
		{
			"id": Guid,
			"name": string,
			"description": string,
			"pricePerNight": double,
            "extras": string[],
            "imagePaths": string[]
		},
		...
	],
    "page": {
        "size": number,
        "totalElements": number,
        "totalPages": number,
        "number": number
    }
}
```
### Availability
```
{
    "available": boolean
}
```
### Booking
```
{
	"id": Guid,
	"userId": Guid,
	"roomId": Guid,
	"from": DateTime,
	"to": DateTime,
	"breakfast": boolean,
	"duration": number,
	"totalPrice": number
}
```
### List of Bookings
```
{
    "bookings": [
        {
            "id": Guid,
            "userId": Guid,
            "roomId": Guid,
            "from": DateTime,
            "to": DateTime,
            "breakfast": boolean,
            "duration": number,
            "totalPrice": number
        },
        ...
    ],
    "page": {
        "size": number,
        "totalElements": number,
        "totalPages": number,
        "number": number
    }
}
```
### User
```
{
	"id": Guid,
	"userMail": string,
	"firstName": string,
	"lastName": string
}
```
### List of Users

```
{
    "users": [
        {
            "id": Guid,
            "userMail": string,
            "firstName": string,
            "lastName": string
        },
        ...
    ],
    "page": {
        "size": number,
        "totalElements": number,
        "totalPages": number,
        "number": number
    }
}
```
### ErrorDto
```
{
	"errorMessage": string
}
```
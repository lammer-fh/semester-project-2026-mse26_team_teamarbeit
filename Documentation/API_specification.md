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
			"pricePerNight": double
		},
		...
	]
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
	"pricePerNight": double
}
```
##### Error
```
{
	"errorMessage": string
}
```

---
### Get Room Images
- **URL**: `/rooms/:id/images`
- **Method**: GET
#### Description
Fetches all images for a given room as base64 encoded strings.
#### Parameters

| Name   | Type     | Description                                                         | Example               |
| :----- | :------- | :------------------------------------------------------------------ | :-------------------- |
| `name` | `string` | Filters images by their name. Case insensitive. Substrings allowed. | `?filter=bathroom`    |
#### Responses
| Code                        | Description                                           | Content                          |
| :-------------------------- | :---------------------------------------------------- | :------------------------------- |
| `200 OK`                    | Success                                               | [List of Image-Data](#list-of-images) |
| `404 Not Found`             | The requested room id does not exist                  | [Error](#errordto)                |
| `500 Internal Server Error` | Something went wrong, please contact our service-desk | [Error](#errordto)                |
##### Success
```
{
	"imageData": string[]
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
#### Responses
| Code                        | Description                                           | Content                        |
| :-------------------------- | :---------------------------------------------------- | :----------------------------- |
| `200 OK`                    | Success                                               | [List of Bookings](#list-of-bookings) |
| `404 Not Found`             | The requested room id does not exist                  | [Error](#errordto)              |
| `500 Internal Server Error` | Something went wrong, please contact our service-desk | [Error](#errordto)              |
##### Success
```
[
	{
		"id": Guid,
		"userMail": string,
		"roomId": Guid,
		"from": DateTime,
		"to": DateTime,
		"breakfast": boolean,
		"duration": number,
		"totalPrice": number
	},
	...
]
```
##### Error

```
{
	"errorMessage": string
}
```
## Images
### Get Image
- **URL**: `/images/:id`
- **Method**: GET
#### Description
Fetches an image by its unique id. Returns image's data as base64 encoded string.
#### Responses
| Code                        | Description                                           | Content             |
| :-------------------------- | :---------------------------------------------------- | :------------------ |
| `200 OK`                    | Success                                               | [Image](#image) |
| `404 Not Found`             | The requested room id does not exist                  | [Error](#errordto)   |
| `500 Internal Server Error` | Something went wrong, please contact our service-desk | [Error](#errordto)   |
##### Success
```
{
	"imageData": string
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
Creates a booking for a user and a room for a selected time range. DateTime is expected in the following format: 'YYYY-MM-DD'.
#### Body
```
{
	"userId": Guid,
	"roomId": Guid,
	"from": DateTime,
	"to": DateTime,
	"breakfast": boolean
}
```
#### Responses
| Code                        | Description                                                                                                        | Content               |
| :-------------------------- | :----------------------------------------------------------------------------------------------------------------- | :-------------------- |
| `201 Created`               | Success                                                                                                            | [Booking](#booking) |
| `400 Bad Request`           | The sent request body was malformed. This includes illegal roomId or userId. Also if "to" is set before "from", or any of them lies in the past. | [Error](#errordto)     |
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
Updates a booking. Idempotent. DateTime is expected in the following format: 'YYYY-MM-DD'.
#### Body
```
{
	"userId": Guid,
	"roomId": Guid,
	"from": DateTime,
	"to": DateTime,
	"breakfast": boolean
}
```
#### Responses
| Code                        | Description                                                                                                  | Content               |
| :-------------------------- | :----------------------------------------------------------------------------------------------------------- | :-------------------- |
| `200 OK`                    | Success                                                                                                      | [Booking](#booking) |
| `400 Bad Request`           | The sent request body was malformed. This includes illegal roomId or userId. Also if "to" is set before "from", or any of them lies in the past. | [Error](#errordto)     |
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
#### Responses
| Code                        | Description                                           | Content                        |
| :-------------------------- | :---------------------------------------------------- | :----------------------------- |
| `200 OK`                    | Success                                               | [List of Bookings](#list-of-bookings) |
| `500 Internal Server Error` | Something went wrong, please contact our service-desk | [Error](#errordto)              |
##### Success
```
[
	{
		"id": Guid,
		"userMail": string,
		"roomId": Guid,
		"from": DateTime,
		"to": DateTime,
		"breakfast": boolean,
		"duration": number,
		"totalPrice": number
	},
	...
]
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
#### Responses
| Code                        | Description                                                        | Content                     |
| :-------------------------- | :----------------------------------------------------------------- | :-------------------------- |
| `200 OK`                    | Success                                                            | [List of Users](#list-of-users) |
| `500 Internal Server Error` | Something went wrong, please contact our service-desk              | [Error](#errordto)          |
##### Success
```
[
	{
		"id": Guid,
		"userMail": string,
		"firstName": string,
		"lastName": string
	},
	...
]
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
#### Responses
| Code                        | Description                                           | Content                        |
| :-------------------------- | :---------------------------------------------------- | :----------------------------- |
| `200 OK`                    | Success                                               | [List of Bookings](#list-of-bookings) |
| `404 Not Found`             | The requested room id does not exist                  | [Error](#errordto)              |
| `500 Internal Server Error` | Something went wrong, please contact our service-desk | [Error](#errordto)              |
##### Success
```
[
	{
		"id": Guid,
		"userMail": string,
		"roomId": Guid,
		"from": DateTime,
		"to": DateTime,
		"breakfast": boolean,
		"duration": number,
		"totalPrice": number
	},
	...
]
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
	"pricePerNight": double
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
			"pricePerNight": double
		},
		...
	]
}
```
### Image
```
{
	"imageData": string
}
```
### List of Images
```
{
	"imageData": string[]
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
[
	{
		"id": Guid,
		"userMail": string,
		"roomId": Guid,
		"from": DateTime,
		"to": DateTime,
		"breakfast": boolean,
		"duration": number,
		"totalPrice": number
	},
	...
]
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
[
	{
		"id": Guid,
		"userMail": string,
		"firstName": string,
		"lastName": string
	},
	...
]
```
### ErrorDto
```
{
	"errorMessage": string
}
```
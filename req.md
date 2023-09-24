# Requirements

## User
1. User account
	* Email verification
	* User able to update their profile image
	* User details
		** Name, email, bio, postion, bio, **phone**, addr, etc...
	* update informations
2. Reset pwd (without logged in)
	* Password reset link should be expried within 1 hour
3. User login (email as username and pwd)
	* Token based authentication (JWT)
	* Refresh Token seamlessly
4. Advoid brute force attack (6 tries)
5. Role and permission based (ACL)
	* Protect application
6. Two-factor authentication (phone number)
	* Send verification code
7. Keep track of user activites
	* Ability to report suspicious activites
	* Tracking information
		* IP address
		* Device
		* Browser
		* Date
		* Type

## Customer
1. Customer Infor
	* Customer can be a person or an institution
	* have "status"
	* have invoices
	* spreadsheet
2. Search
	* search by name
	*pagination

##Invoices
1. Manange invoices
	* create
	* add to customer
	* print and mailling
	* spreadsheet
	* download as pdf
2. Upon request...

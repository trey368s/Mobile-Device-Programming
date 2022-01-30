# README
##  Introduction
Our team has decided to create a mobile version of a fast food application. This is where customers can look at our menu items, add them to their cart, and check out thier order. Kitchen staff and servers  should be able to see online orders as they are being made online so they can anticipate customer needs. Servers wil also have thier side of the app where they will be able to recieve an view existing orders as well as update their order status.
##  Story Board
![0T}HL9OU)7R}70TNLDFJXQH](https://user-images.githubusercontent.com/81964248/151697018-57566267-ea92-43f1-af54-3393045b6eb4.png)

![}}DI%`OX{3M_%%HOQ8IDX@J](https://user-images.githubusercontent.com/81964248/151697025-2bd7d9ff-d19d-4767-8551-40949b4676a4.png)

![TQR377(5VDA%4NGV__32SM6](https://user-images.githubusercontent.com/81964248/151697029-3813d2ca-cd32-4256-a9e8-2228a0aa63db.png)

![36QWMV25YY~`4ECAZ)17%BK](https://user-images.githubusercontent.com/81964248/151697055-d923328a-0ff2-4ba6-ad40-0511188ebd0b.png)
## Functional Requirements
### Requirement 1: View menu
#### Dependencies
Restaurant is open for business, fully stocked and synced with the app.
#### Scenario
As a customer, I want to be able to quickly search the menu for particular items based on name, description, or ingredients.
### Example 1
**Given** that the app is online, the restaurant is open, and no menu items are out of stock

**When** I search for “hamburgers”

**Then** I should see all menu items categorized as hamburgers:

*Cheeseburger*

A simple burger with American cheese

$4.99

*Bacon Burger*

A hamburger topped with bacon

$7.99

*Guacamole Burger*

A hamburger topped with guacamole.

$9.99

### Example 2
**Given** that the app is online, the restaurant is open, and no menu items are out of stock

**When** I search for “Bacon Burger”

**Then** I should see only the Bacon Burger:

*Bacon Burger*

A hamburger topped with bacon

$7.99

### Example 3
**Given** that the app is online, the restaurant is open, and no menu items are out of stock

**When** I search for “asdjflj”

**Then** I should receive no results, as my input is nonsense

### Requirement 2: Label menu items which are out of stock
#### Dependencies
Restaurant is open for business, but some menu items are not available.
#### Scenario
As an employee, I want to be able to report supply shortages through the app to prevent unfulfillable orders from being placed.
### Example 1
**Given** that the app is online, I am logged in as an employee, and bacon is out of stock

**When** I set the quantity of bacon to 0 from the inventory management screen

**Then** a customer who searches for “Bacon Burger” should see the bacon burger marked with an “out of stock” notification in prominent red letters:

*Bacon Burger* <span style="color:red">(Out of Stock)</span>

A hamburger topped with bacon

$7.99

Attempting to add the bacon burger to cart will result in an error message preventing the action.

### Example 2
**Given** that guacamole is available at the start of a business day, the app should still decrement the inventory count of guacamole whenever a menu item containing guacamole is sold

**When** I set the quantity of bacon to 0 from the inventory management screen

**Then** a customer purchases a Guacamole Burger, thus expending that final unit of guacamole, all menu items that require guacamole will be automatically flagged as out of stock, without manual employee intervention. Now, if the next customer searches for “nachos”, they will receive the following result:

*Nachos with Salsa*

Corn chips with a side of salsa

$1.99

*Nachos with Cheese*

Corn chips topped with melted cheese

$2.99

*Nachos with Guacamole* <span style="color:red">(Out of Stock)</span>

Corn chips served with a side of guacamole

$3.99

### Requirement 3: Add and remove menu items
#### Dependencies
Restaurant is open for business and a new menu item is being introduced, or an underperforming menu item is being discontinued.
#### Scenario
As an administrator, I want to be able to add items to the menu, as well as permanently remove items from the menu.
### Example 1
**Given** that the app is online and I am logged in as an administrator

**When** I add the BLT Sandwich to the menu, specifying its description as “Bacon, lettuce, and tomato on toasted rye bread, seasoned with mayonnaise” and its price as $9.99

**Then** a customer who searches for “sandwiches” should see the following results:

*Club Sandwich*

Turkey and ham on white bread

$7.99

*BLT Sandwich*

Bacon, lettuce, and tomato on toasted wheat bread, seasoned with mayonnaise

$9.99

*Reuben Sandwich*

Beef and sauerkraut with grilled Swiss cheese on rye bread

$12.99

### Example 2
**Given** that the app is online and I am logged in as an administrator

**When** I remove the Club Sandwich (our least popular option) from the menu

**Then** a customer who searches for “sandwiches” should see the following results:

*BLT Sandwich*

Bacon, lettuce, and tomato on toasted wheat bread, seasoned with mayonnaise

$9.99

*Reuben Sandwich*

Beef and sauerkraut with grilled Swiss cheese on rye bread

$12.99
## Class Diagram
![image](https://user-images.githubusercontent.com/81964248/151712773-f30cdd04-1af2-4608-be99-ed6e0c56386f.png)
## Class Diagram Description
- MainActivity: First screen the users sees. It shows options to start an order, browse the menu, access profile, and past orders if applicable. 
- CustomerDetailsActivity: Profile page for Customer.
- ProductDetailActivity: Details page for each product on offer.
- OrderDetailActivty: Details page for the current order.
- Customer: Noun class that represents a Customer.
- Product: Noun class that represents a Product.
- Order: Noun class that represents an Order.
- iCustomerDAO: Interface to persist Customer data.
- iProductDAO: Interface to persist Product data.
- iOrderDAO: Interface to persist Order data.

## Scrum
UI specialist: Bangyan Ju,Cepada Morgan
Product Owner:Trey Stegeman
Integration Specialist:Marco Bahns,Jeremy Shepherd
## Teams Link
https://teams.microsoft.com/l/meetup-join/19%3ameeting_ZDBhOTlkYTUtMDk5My00MmEzLWE3NTItOWU2YmIzY2VlNjk0%40thread.v2/0?context=%7b%22Tid%22%3a%22f5222e6c-5fc6-48eb-8f03-73db18203b63%22%2c%22Oid%22%3a%224e8ab8be-5ad6-4e0a-b7bd-90ed20a1675b%22%7d

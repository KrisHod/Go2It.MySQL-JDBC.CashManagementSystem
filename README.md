1. Create a Java representation (entities) of SQL tables created in CASH_MANAGEMENT database.

2. Create a specific class and method that will show a total sum paid for a merchant with a given id (argument). 
The report should also contain merchant_id, title and lastSent info

3. Create an application to display a list of all merchants sorted alphabetically in descending order

4. Add a few more payments with updating corresponding columns in Merchant table

5. Create a method that will send funds to a merchant if the needToSend>minSum. This method should update the sent and lastSent columns in the Merchant table

6. Refactor the code based on Entity/Repository/Service pattern

7. Find the most active customer based on the number of order within the passed in time period (ie week, month, quarter, year). 
The resulting Customer object should contain the list of all Payments made.

8. Create a CSV file loader class to load the data for your system in CSV format. Three files will be served:

● Merchants_2020_03_08.csv with Merchants information. 

● Customers_2020_03_08.csv with Customers information. 

● Payments_2020_03_08.csv with Payments information. 

Note 1: while the records from files are loaded, we need to guarantee that the data duplication will not occur.
We need to check the name of the Merchant and Customer ignoring the text case.

Note 2: Since there is no way for us to know beforehand that ID will be assigned to loaded merchants and customers,
Payments CSV file is based on the assumption that customers` and merchants` names are unique

9. Extra tough – major refactor is required:

● Create new table Product with columns: id, description, createdOn, modifiedOn, price.

● Create new table Order that will be a link between a Payment and Product. 
Each order will contain references to Payment associated with the Order and List of Products related to the it.

i. New table will contain: id, deliveryAddress, customerId, paymenttId, orderProductListId

ii. Note: One Product can be used in multiple Orders and one Order can contain multiple Goods – that’s why we have to use orderProductListId. 
So we need to employ Many-to-Many relation (extra table is required. Draw it first to understand the problem and then Google for more details)

● Refactor the Payment table to use a foreign key to Order table instead of hard-coded description of good.

● Create repository for Product and Order tables. Note, Order object will be constructed using the SQL join operator instead of using the composition of repositories in OrderService.

● Create several records in Product, Order and related many-to-many table using the code in the repository.

● Create a report with three most popular products for each merchant based on number of purchases.

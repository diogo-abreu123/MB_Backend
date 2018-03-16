The repository has an ant file named mb_backend.xml on the root folder. Executing the ant file will allow the user to use a browser for the web app.
The web app uses the data.json file at startup, so it should be located at the root folder.

API Documentation:

The API has three main functions:

/bookTestDrive - allows the user to book a test drive. As input the function receives the vehicle id, first and last name of the client and pickup date.
Variables: String vehicleID
			String firstName
			String lastName
			String pickupDate, with format yyyy-mm-ddThh:mm:ss
Usage example: "http://localhost:8080/bookTestDrive?vehicleID=1cd6eae7-5f6f-42a7-a4ca-de7e498d9ce4&firstName=Jon&lastName=Snow&pickupDate=2019-03-03T10:00:00"


/filter - allows the user to list cars using to one of four filters: Model, Fuel, Transmission and Dealer
Variables: String type, which defines the filter and can be one of four options (model, fuel, transmission, dealer)
			String value, specifies the corresponding value for the filter.
Usage example: "http://localhost:8080/filter?type=transmission&value=AUTO"


/closestDealer - allows the user to select characteristics for a car. The function returns the closest dealer with a car that has the desired characteristics.
Variables: String model
			String fuel
			String transmission
			BigDecimal latitude
			BigDecimal longitude
Usage example: "http://localhost:8080/closestDealer?model=AMG&fuel=ELECTRIC&transmission=AUTO&lat=32&long=-9"


Due to time constraints no unit tests were made.
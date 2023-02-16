# Forex App

This is the MVVM architecture that I made for this project.

![SeekerCapitalApp drawio](https://user-images.githubusercontent.com/3623363/207842814-44357fd6-5869-4320-a166-1edaae190b00.png)

Engineering rationalization that I decide for the features: 
- Mechanical for saving the original price value 
The first time application calls the API, the application will delete the previous original price value and save the new one. This mechanism is built for calculating the % change from the first price you receive once you launch the app
Every 5 seconds, this app will update its value by calling API; because the value from API is static, I add a random value in MarketRepositoryImpl. 
- I use shared preference to save the current money that the user has (in this case, $10,000). I put the calculation of equity and balance in the repository to prepare if there is an extended requirement (call API for both of the calculations, for example)

Additional Information:
- In the architecture, I use DI in Presentation, Repository, SharedPreference, DB, Network
- In the release and debug version, I call API from:
https://apilayer.com/marketplace/currency_data-api
- In the mock version, I call API from:
https://dcf8d910-dfc4-451d-8787-7acede2d481e.mock.pstmn.io

Additional features:
-  I also encode the App Key ID to make this app more secure


# SeekerCapitalApp

This is the MVVM architecture that I made for this project

![SeekerCapitalApp drawio](https://user-images.githubusercontent.com/3623363/207842814-44357fd6-5869-4320-a166-1edaae190b00.png)


- Mechanical for saving original Price 
The first time application calls the API, it will delete the previous one and save it as an original price
Every 5 seconds, this app will update its value by calling a new API; because the value from API is static, I add a random value in MarketRepositoryImpl

- I use DI in Presentation, Repository, SharedPreference, DB, Network

- In the release and debug version, I call API from:
https://apilayer.com/marketplace/currency_data-api?utm_source=apilayermarketplace&utm_medium=featured
- In the mock version, I call API from:
https://dcf8d910-dfc4-451d-8787-7acede2d481e.mock.pstmn.io


- Shared preference to save the current nominal that the user has (in this case, $10,000.00). I put the calculation of equity and balance in the repository to prepare if there is an extended requirement (call API, for example)


- Additionally, I also encode the App Key ID to make this app more secure


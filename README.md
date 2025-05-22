# Currency GIF Service

## Description

This application displays a GIF based on the exchange rate fluctuation of a selected currency against a base currency (USD by default).
- If the exchange rate of the selected currency has increased compared to the previous day, a "rich" GIF is displayed.
- If the exchange rate has decreased, a "broke" GIF is displayed.
- If the rate remains unchanged, an "equal" GIF is displayed.

The application fetches exchange rates from [Open Exchange Rates](https://openexchangerates.org/) and GIFs from [Giphy](https://giphy.com/).

## Prerequisites

- Java Development Kit (JDK) 17 or higher.
- Apache Maven.

## Configuration

1.  **API Keys**:
    You need API keys from Open Exchange Rates and Giphy.
    - Open Exchange Rates API key: Sign up at [https://openexchangerates.org/signup](https://openexchangerates.org/signup)
    - Giphy API key: Create an app at [https://developers.giphy.com/dashboard/](https://developers.giphy.com/dashboard/)

2.  **Set API Keys in `application.properties`**:
    Open the file `src/main/resources/application.properties`.
    You will find the following lines:
    ```properties
    rates.url=https://openexchangerates.org/api
    client.url=https://api.giphy.com/v1/gifs/search
    rates_api_key=YOUR_OPEN_EXCHANGE_RATES_APP_ID
    giphy.api.key=YOUR_GIPHY_API_KEY
    # server.port=8081 # Optional: Uncomment to change the default port
    ```
    Replace `YOUR_OPEN_EXCHANGE_RATES_APP_ID` and `YOUR_GIPHY_API_KEY` with your actual API keys.
    The existing Giphy key (`x5ne24uuC6kFZLiAiQN2WLnH5ae0xBkI`) is a public test key from Giphy documentation and might have limitations. It's recommended to use your own.

## How to Build and Run

1.  **Clone the repository** (if you haven't already).
2.  **Navigate to the project root directory.**
3.  **Build the application using Maven**:
    ```bash
    mvn clean install
    ```
4.  **Run the application**:
    ```bash
    mvn spring-boot:run
    ```
    Alternatively, you can run the packaged JAR file from the `target` directory:
    ```bash
    java -jar target/currency_Alfa-0.0.1-SNAPSHOT.jar
    ```
    The application will start, typically on port 8080 (unless configured otherwise in `application.properties`).

## How to Use

1.  **Open your web browser** and go to `http://localhost:8080/` (or your configured port).
2.  You will see a page displaying a list of available currencies and their current rates against USD.
3.  **Select a currency code** from the dropdown list.
4.  **Click the "Get GIF" button.**
5.  The application will then display a GIF that reflects whether the selected currency's value went up ("rich"), down ("broke"), or stayed the same ("equal") compared to the previous day's rates.
6.  An error page will be displayed if the currency code is invalid, data cannot be fetched, or any other issue occurs.

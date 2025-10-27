# Numbeo API Java Client

A Java application that fetches and displays cost of living prices for cities using the [Numbeo API](https://www.numbeo.com/api/).

## Features

- Fetch city prices from Numbeo API
- Display formatted price information including:
  - Average, lowest, and highest prices
  - Item categories
  - Data points and contributors
  - Currency information
- Command-line interface
- Configurable via properties file, environment variables, or system properties
- Comprehensive logging with SLF4J and Logback

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Numbeo API key (get one from [Numbeo API Documentation](https://www.numbeo.com/api/api_documentation.jsp))

## Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd numbeo-api-java
```

2. Build the project:
```bash
mvn clean package
```

## Configuration

You can configure your Numbeo API key in one of three ways:

### Option 1: Environment Variable (Recommended)
```bash
export NUMBEO_API_KEY=your_api_key_here
```

### Option 2: Properties File
Edit `src/main/resources/application.properties`:
```properties
api.key=your_api_key_here
```

### Option 3: System Property
```bash
java -Dapi.key=your_api_key_here -jar target/numbeo-api-java-1.0.0.jar
```

## Usage

### Using Maven

Run with default city (San Francisco, CA):
```bash
mvn exec:java -Dexec.mainClass="com.numbeo.api.NumbeoApp"
```

Run with custom city and country:
```bash
mvn exec:java -Dexec.mainClass="com.numbeo.api.NumbeoApp" -Dexec.args="'New York, NY' 'United States'"
```

### Using the JAR file

After building with `mvn clean package`, run:

```bash
# Default city (San Francisco, CA)
java -jar target/numbeo-api-java-1.0.0.jar

# Custom city and country
java -jar target/numbeo-api-java-1.0.0.jar "London" "United Kingdom"

# Another example
java -jar target/numbeo-api-java-1.0.0.jar "Tokyo" "Japan"
```

## Example Output

```
╔══════════════════════════════════════════════════════════════════════════════╗
║                         NUMBEO PRICE INFORMATION                             ║
╚══════════════════════════════════════════════════════════════════════════════╝

City:        San Francisco, CA
Country:     United States
Currency:    USD
Last Update: 10/2024
Contributors (12 months): 245

╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                 PRICES                                                               ║
╠══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣
║ Item                                               │ Average      │ Range (Low - High)     │ Data Points ║
╠══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣
╟──────────────────────────────────────────────────────────────────────────────────────────────────────────────────╢
║   RESTAURANTS                                                                                                        ║
╟──────────────────────────────────────────────────────────────────────────────────────────────────────────────────╢
║ Meal, Inexpensive Restaurant                       │ USD 25.00    │ 15.00 - 40.00          │          156 ║
║ Meal for 2 People, Mid-range Restaurant            │ USD 100.00   │ 60.00 - 150.00         │          189 ║
...
```

## Project Structure

```
numbeo-api-java/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/numbeo/api/
│   │   │       ├── NumbeoApp.java              # Main application class
│   │   │       ├── client/
│   │   │       │   └── NumbeoApiClient.java    # HTTP client for Numbeo API
│   │   │       └── model/
│   │   │           ├── CityPricesResponse.java # Response model
│   │   │           └── PriceItem.java          # Price item model
│   │   └── resources/
│   │       ├── application.properties          # Configuration file
│   │       └── logback.xml                     # Logging configuration
│   └── test/
│       └── java/                               # Test classes (to be added)
├── pom.xml                                     # Maven configuration
└── README.md                                   # This file
```

## Dependencies

- **OkHttp** (4.11.0) - HTTP client for API requests
- **Gson** (2.10.1) - JSON serialization/deserialization
- **SLF4J** (2.0.9) - Logging facade
- **Logback** (1.4.11) - Logging implementation
- **JUnit** (5.10.0) - Testing framework

## API Reference

The application uses the Numbeo City Prices API endpoint:
```
https://www.numbeo.com/api/city_prices?city={city}&country={country}&api_key={api_key}
```

For more information, visit the [Numbeo API Documentation](https://www.numbeo.com/api/api_documentation.jsp).

## Error Handling

The application provides clear error messages for common issues:
- Missing or invalid API key
- Network connectivity problems
- Invalid city or country names
- API rate limiting or server errors

## Logging

Logs are written to:
- Console (INFO level and above)
- `logs/numbeo-api.log` (rolling daily, 30-day retention)

To change log levels, edit `src/main/resources/logback.xml`.

## Development

### Building from Source

```bash
mvn clean install
```

### Running Tests

```bash
mvn test
```

### Code Style

The project follows standard Java coding conventions. Please ensure your IDE is configured to use:
- 4 spaces for indentation
- UTF-8 encoding
- Unix-style line endings (LF)

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Write tests for new functionality
5. Submit a pull request

## License

This project is open source and available under the MIT License.

## Troubleshooting

### API Key Issues
- Ensure your API key is valid and active
- Check that the key is properly configured (no extra spaces)
- Verify you haven't exceeded your API rate limits

### City Not Found
- Try different city name formats (e.g., "New York, NY" vs "New York City")
- Ensure the country name matches Numbeo's database
- Check the [Numbeo website](https://www.numbeo.com/) to verify city availability

### Build Issues
- Ensure Java 11+ is installed: `java -version`
- Verify Maven is installed: `mvn -version`
- Clear Maven cache: `mvn clean` and rebuild

## Support

For issues related to:
- **This application**: Open an issue in this repository
- **Numbeo API**: Contact [Numbeo Support](https://www.numbeo.com/common/contact.jsp)
- **API pricing and limits**: Visit [Numbeo API Documentation](https://www.numbeo.com/api/)

## Author

Created as an example application for integrating with the Numbeo API.

# IP Calculator

## 🌟 Description
A modern web application built with Spring Boot that calculates IP addresses and subnets.

## 🚀 Technical Stack
- **Backend:**
  - Java 17
  - Spring Boot
  - Spring MVC
  - Thymeleaf (template engine)

- **Frontend:**
  - HTML5
  - CSS3

## ✨ Features
- IP address calculations
  - Subnet mask determination
  - Network and broadcast address calculation
  - Available host range calculation
- Two calculation methods:
  - FLSM (Fixed Length Subnet Mask)
  - VLSM (Variable Length Subnet Mask)
- User-friendly web interface
- Results download functionality
- Input validation
- Error handling
- Clean and modern UI design


## 🔧 Requirements
- Java JDK 17 or higher
- Maven 3.6+
- Spring Boot 3.x
- Modern web browser

## 📦 Installation

1. Clone the repository:
```bash
git clone https://github.com/DoriXon55/calculatorIP.git
```

2. Navigate to the project directory:
```bash
cd calculatorIP
```

3. Build the project with Maven:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

5. Access the application at:
```
http://localhost:8080
```

## 🛠️ Development Setup
1. Install your preferred IDE (recommended: IntelliJ IDEA, Eclipse, or VS Code)
2. Import the project as a Maven project
3. Make sure you have the following plugins/extensions:
   - Spring Boot Tools
   - Java Extension Pack
   - Maven for Java

## 🔍 Project Structure
```
calculatorIP/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/netcalc/IPCalculator/
│   │   │       ├── controllers/
│   │   │       ├── services/
│   │   │       ├── models/
│   │   │       └── IpCalculatorApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   └── js/
│   │       └── templates/
│   └── test/
│       └── java/
└── pom.xml
```

## 🎯 Future Improvements
- [ ] Add IPv6 support (maybe)

## 👤 Author
- [@DoriXon55](https://github.com/DoriXon55)

## 📄 License
This project is licensed under the MIT License - see the [LICENSE](https://opensource.org/license/mit) file for details.

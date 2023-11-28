<a name="readme-top"></a>

# purchase-transaction-api

<!--suppress HtmlUnknownAnchorTarget, HtmlDeprecatedAttribute -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#license">License</a></li>
  </ol>
</details>

## About The Project

The project consists of implementing a purchase transaction system that has two main functionalities:

* Store a purchase transaction.
* Retrieve a purchase transaction in a specified country’s currency.

### Built With

* Java
* Spring Boot
* MongoDb
* Embedded MongoDb (only for tests)

## Getting Started

### Prerequisites

* Java 17
* Docker
* Docker-Compose

### Installation

#### 1. Build

  ```sh
  ./gradlew build -x test
  ```
  
#### 2. Run
  ```sh
  docker-compose up
  ```

## Usage

  [http://localhost:8080](http://localhost:8080/)

## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>


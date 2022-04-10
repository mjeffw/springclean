# springclean

**Demo of SpringBoot + Clean Architecture**

This Java/Maven project illustrates one way to design a SpringBoot application to be compatible with the Clean (Hexagonal) architecture principles. It's not a fully working application; specifically, all the adapters except `adapter.restapi` are just stubs.

## Dissection of the code

The application is divided into 3 main types of components:

-   A single _domain_ (the `domain` maven submodule).
-   _Adapters_ (the maven submodules that start with `adapter-`).
-   The _application_ (maven submodule `application`).

### Domain

Contains all the business logic of the application, either as an "entity" or a "usecase".

### Entity

Entities are "business objects" from the business domain, such as Invoice, LineItem, Customer, etc. They contain business logic to validate their data, ensure "structural" business rules are met, etc.

Examples of entity business rules:

-   An Invoice is associated to a Sale made to a specific Customer.
-   Invoices are composed of one or more Line Items.
-   A Line Item represents some quantity of Product sold at a specific unit price.
-   The total cost of a Line Item is its (unit price &times; quantity sold).
-   The total cost of an Invoice is the sum of its Line Item costs.

### Use Case

Use Case is an example of a specific business "functional flow", typically orchestrating entities to achieve some business value.

For example, a Make Payment use case would involve accepting a payment from a Customer, and applying the amount of that payment to the amount owed on an Invoice.

### Port

Supporting the Entities and Use Cases are "Ports". A Port is a logical I/O port to the domain -- all communication between the domain and the rest of the application are through Ports. In Java, a Port is an interface.

All Ports used by the domain are defined in the domain. This prevents the need for the domain having any dependencies to the rest of the code.

An incoming Port -- an interface called by code outside the domain to access the business logic inside the domain -- is sometimes called a "primary" port. These are always implemented by a Use Case.

An outgoing Port -- those that the domain calls -- are defined in the domain, but implemented by code outside of the domain, by an adapter.

## Architectural Rules

-   The Domain must not depend on any frameworks, including Spring and SpringBoot itself.
-   The Domain must not depend on code outside of the Domain package.
-   Domain Entities have no other dependencies even to other code inside the Domain package.
-   Use Cases depend upon Entities and Ports, and their associated data payload.
-   With one exception, the data passed between the domain and the adapters are always "primitives" (int, double, boolean, String, Date, etc.) or simple DTOs or Java 17 records. They just carry data, and do not have any significant behavior or methods. In this example code, I've added a new type: `Money`, which is defined inside the `domain` maven submodule, but not in the domain's package structure (`..springclean.domain..`).
    -   The exception is a special port/adapter that retrieves Entities from some form of persistent storage. These are called Entity Gateways.

## Package Structure

This package structure is used by all submodules as appropriate to the purpose of that submodule.

-   `us.hypermediocrity.springclean` - The base package; all code belongs to this hierarchy. This is where the Spring Boot application is defined, along with any needed factories that will be injected by Spring into Use Cases or Adapters.
    -   `adapter` - Each adapter is declared as a subpackage of the `adapter` package.
        -   `bankaccount` - The Bank Account implementation of the `PaymentService` port (stub).
        -   `creditcard` - The Credit Card implementation of the `PaymentService` port (stub).
        -   `currencyexchange` - the Yahoo Finance implementation of the `CurrencyExchange` port (stub).
        -   `customers` - the implementation of the `Customers` entity gateway port (stub).
        -   `invoices` - the implementation of the `Invoices` entity gateway port (stub).
        -   `paymentrouter` - A special implementation of the `PaymentService` port that forwards the request to one of the other `PaymentService` adapters based on the value of the `PaymentType` enumeration.
        -   `paypal` - The PayPal implementation of the `PaymentService` port.
    -   `common` - Used for defining non-entity, common data types used throughout the application. Be careful that no business logic is implemented in this package.
    -   `domain` - The Domain, including all Entities, Use Cases, and Ports.
        -   `entity` - Business object 'entities' are defined here.
        -   `usecase` - Our Use Case interfaces and implementations are defined here.
            -   `common` - Any types used as parameters or return types in a Use Case are defined in this package.
        -   `port` - The Port interfaces and any types used as parameters or return values are declared here.

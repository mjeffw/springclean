# springclean
Demo of SpringBoot + Clean Architecture

## What is Architecture?
 - Big ‘A’ vs. little ‘a’ architecture
 - Big A - make sure your application works in its context.
 - Little A - How easy is your application to understand and change?
 - Support deferring decisions until the last responsible moment.
 - What makes an architecture “good”?
 - SOLID principles

## What is Clean Architecture?
 - Brief history
 - Goals
 - Framework-independent
 - Testable
 - Independent of associated infrastructure
 - UI, Database, external APIs
 - Super clear on what needs to change based on changing requirements
 - Structure:
    - Entities
    - Usecases
    - Adapters
    - Frameworks, Drivers, APIs, etc.

How to implement Clean Architecture?
 - Show a use-case — UI View Invoice
 - Walk through a sequence diagram
 - Walk through architecture
 - Add a new use-case — Email invoice
 - Show changes needed

 - What about per-API data, such as payments (CC needs card number, holder name, expiry date, PIN/security code, ZIP code; but direct bank access needs account number, routing number, etc.; while PayPal needs authorization id)

Wrap up idea:
 - Ask questions about types of changes and what code needs to be modified
 - 


“Their shift in design was to architect the system’s interfaces ‘’by purpose’’ rather than by technology, and to have the technologies be substitutable (on all sides) by adapters.”

“Distributed, Large-Team Development
Teams in different locations all build to the Hexagonal architecture, using FIT and mocks so the applications or components can be tested in standalone mode. The CruiseControl build runs every half hour and runs all the applications using the FIT+mock combination. As application subsystem and databases get completed, the mocks are replaced with test databases.”
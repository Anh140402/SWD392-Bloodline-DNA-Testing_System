# System Design Documentation

## 1. System High-Level Design

```plantuml
@startuml
skinparam componentStyle uml2

component "Client Application" as Client
component "API Gateway/Controller Layer" as API
component "Authentication/Security" as Auth
component "Service Layer" as Service
component "Repository Layer" as Repo
database "Database" as DB

Client --> API : HTTP/REST
API --> Auth : Authenticate
API --> Service : Process
Service --> Repo : CRUD
Repo --> DB : Persist
@enduml
```

## 2. State Diagram for Test Order Process

```plantuml
@startuml
skinparam StateFontStyle bold

[*] --> Created : Create Test Order
Created --> SampleCollected : Collect DNA Sample
SampleCollected --> Testing : Start Testing
Testing --> Completed : Complete Test
Testing --> Failed : Test Failed
Completed --> [*]
Failed --> Created : Retry Test

state Created
state SampleCollected
state Testing
state Completed
state Failed
@enduml
```

## 3. Communication Diagram

```plantuml
@startuml
skinparam sequenceMessageAlign center

actor Client
participant Controller
participant AuthService
participant Service
participant Repository
database Database

Client -> Controller : HTTP Request
Controller -> AuthService : Validate JWT
AuthService --> Controller : Token Valid
Controller -> Service : Process Request
Service -> Repository : CRUD Operation
Repository -> Database : Execute Query
Database --> Repository : Return Data
Repository --> Service : Return Result
Service --> Controller : Return Response
Controller --> Client : HTTP Response
@enduml
```

## 4. Integrated Communication Diagram

```plantuml
@startuml
skinparam componentStyle uml2

package "Client Layer" {
    component "Web UI" as UI
    component "Mobile App" as Mobile
}

package "API Layer" {
    component "API Gateway" as API
    component "JWT Auth" as Auth
    component "OpenAPI/Swagger" as Swagger
}

package "Service Layer" {
    component "Account Service" as AS
    component "Test Service" as TS
    component "Sample Service" as SS
    component "Blog Service" as BS
    component "Feedback Service" as FS
}

package "Data Layer" {
    component "Repositories" as Repo
    database "SQL Server" as DB
}

UI -down-> API : REST
Mobile -down-> API : REST
API -down-> Auth
API -down-> Swagger

API -down-> AS : Authenticated Request
API -down-> TS : Authenticated Request
API -down-> SS : Authenticated Request
API -down-> BS : Authenticated Request
API -down-> FS : Authenticated Request

AS -down-> Repo
TS -down-> Repo
SS -down-> Repo
BS -down-> Repo
FS -down-> Repo

Repo -down-> DB
@enduml
```

## Mô tả chi tiết

### 1. System High-Level Design
- Kiến trúc nhiều lớp (Layered Architecture)
- Sử dụng REST API để giao tiếp
- JWT Authentication cho bảo mật
- Repository pattern cho tương tác database

### 2. State Diagram
- Mô tả quy trình xét nghiệm ADN từ lúc tạo đơn đến hoàn thành
- Các trạng thái chính: Created, SampleCollected, Testing, Completed, Failed
- Cho phép retry khi test thất bại

### 3. Communication Diagram
- Luồng xử lý request từ client đến database và ngược lại
- Tích hợp JWT authentication
- Xử lý đồng bộ theo tuần tự

### 4. Integrated Communication
- Tích hợp đầy đủ các thành phần của hệ thống
- Phân chia rõ ràng các layer
- Hiển thị luồng dữ liệu và tương tác giữa các service 
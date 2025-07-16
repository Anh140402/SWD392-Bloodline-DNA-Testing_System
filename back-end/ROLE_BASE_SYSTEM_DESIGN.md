# Role-Based System Design Details

## System High-Level Design (Overall)

```plantuml
@startuml
left to right direction

cloud "Client Layer" {
    component "Web Application" as Web
}

rectangle "API Gateway Layer" {
    component "Load Balancer" as LB
    component "API Gateway" as Gateway
}

rectangle "Security Layer" {
    component "Authentication" as Auth
    component "Authorization" as Authz
    component "JWT Manager" as JWT
}

rectangle "Service Layer" {

        component "Account Service" as AS
        component "Test Service" as TS
        component "Sample Service" as SS
        component "Appointment Service" as APS
        component "Order Service" as OS
        component "Report Service" as RS
        component "Blog Service" as BS
        component "Feedback Service" as FS
}

rectangle "Repository Layer" {
    component "Account Repository" as AR
    component "Appointment Repository" as APR
    component "Blog Repository" as BR
    component "Feedback Repository" as FR
    component "Kit Repository" as KR
    component "Sample Repository" as SR
    component "Service Repository" as SVR
    component "Test Order Repository" as TR
    component "Test Report Repository" as RR
}

database "SQL Server" as DB

' Client to Gateway
Web --> LB : HTTP/REST
LB --> Gateway : HTTP/REST

' Security Flow
Gateway --> Auth : Authenticate
Auth --> JWT : Generate Token
JWT --> Authz : Validate Token

' Service Access
Authz --> AS : Process
Authz --> TS : Process
Authz --> SS : Process
Authz --> APS : Process
Authz --> OS : Process
Authz --> RS : Process
Authz --> BS : Process
Authz --> FS : Process

' Repository Layer
AS --> AR : CRUD
APS --> APR : CRUD
BS --> BR : CRUD
FS --> FR : CRUD
SS --> SR : CRUD
SS --> KR : CRUD
TS --> SVR : CRUD
OS --> TR : CRUD
RS --> RR : CRUD

' Database Access
AR --> DB : Persist
APR --> DB : Persist
BR --> DB : Persist
FR --> DB : Persist
KR --> DB : Persist
SR --> DB : Persist
SVR --> DB : Persist
TR --> DB : Persist
RR --> DB : Persist

note bottom of Gateway
  API Gateway Features:
  * Request Routing (HTTP/REST)
  * API Composition
  * Protocol Translation
  * Request/Response Handling
end note

note bottom of Auth
  Security Features:
  * OAuth2/JWT Authentication
  * Role-based Authorization
  * Token Validation
  * Session Management
end note

note bottom of AR
  Repository Layer:
  * JPA Repository
  * CRUD Operations
  * Data Access Logic
  * Transaction Management
end note

@enduml
```

## State Diagrams

### Account Service State
Description: Manages user account lifecycle with security states and authentication flow.

```plantuml
@startuml
skinparam StateFontStyle bold

[*] --> Inactive : Register
Inactive --> Active : Verify Email
Active --> Locked : Multiple Failed Logins
Locked --> Active : Reset Password
Active --> Suspended : Admin Action
Suspended --> Active : Admin Action
Active --> [*] : Delete Account

state Active {
    [*] --> LoggedOut
    LoggedOut --> LoggedIn : Login
    LoggedIn --> LoggedOut : Logout
}

note right of Active
  Active states include:
  * Normal operations
  * Profile management
  * Password changes
end note
@enduml
```

### Test Order Service State
Description: Orchestrates DNA testing workflow from order creation to result delivery.

```plantuml
@startuml
skinparam StateFontStyle bold

[*] --> Created : Create Order
Created --> Scheduled : Book Appointment
Scheduled --> InProgress : Start Test
InProgress --> SampleCollected : Collect Sample
SampleCollected --> Processing : Process Sample
Processing --> Completed : Generate Result
Processing --> Failed : Test Failed
Failed --> Processing : Retry Test
Completed --> [*] : Close Order

note right of Processing
  Processing includes:
  * DNA extraction
  * Analysis
  * Quality control
end note
@enduml
```

### Appointment Service State
Description: Handles scheduling process with confirmation, cancellation, and completion states.

```plantuml
@startuml
skinparam StateFontStyle bold

[*] --> Requested : Create Appointment
Requested --> Confirmed : Staff Confirms
Requested --> Rejected : Staff Rejects
Confirmed --> InProgress : Start Appointment
InProgress --> Completed : Finish Appointment
InProgress --> Cancelled : Cancel
Confirmed --> Cancelled : Cancel Before Start
Cancelled --> [*]
Completed --> [*]
Rejected --> [*]

note right of Confirmed
  Confirmed state:
  * Time slot reserved
  * Staff assigned
  * Resources allocated
end note
@enduml
```

### Sample Service State
Description: Controls DNA sample handling from collection through testing to disposal.

```plantuml
@startuml
skinparam StateFontStyle bold

[*] --> Registered : Register Sample
Registered --> Collected : Collect Sample
Collected --> QualityCheck : Check Quality
QualityCheck --> Valid : Pass Check
QualityCheck --> Invalid : Fail Check
Valid --> InStorage : Store Sample
Invalid --> Rejected : Reject Sample
InStorage --> InTesting : Start Test
InTesting --> Consumed : Complete Test
Rejected --> [*]
Consumed --> [*]

note right of QualityCheck
  Quality checks:
  * Sample integrity
  * Quantity check
  * Contamination check
end note
@enduml
```

### Kit Service State
Description: Tracks testing kit lifecycle and allocation to test orders.

```plantuml
@startuml
skinparam StateFontStyle bold

[*] --> Available : Add Kit
Available --> Reserved : Assign to Order
Reserved --> InUse : Start Test
InUse --> Available : Complete Test
InUse --> Damaged : Report Damage
Available --> Expired : Expire
Damaged --> [*] : Dispose
Expired --> [*] : Dispose

note right of Available
  Available state:
  * Ready for use
  * Quality verified
  * Within expiry date
end note
@enduml
```

### Blog Service State
Description: Manages content workflow through draft, review, and publication states.

```plantuml
@startuml
skinparam StateFontStyle bold

[*] --> Draft : Create Post
Draft --> UnderReview : Submit
UnderReview --> Published : Approve
UnderReview --> Rejected : Reject
Published --> Archived : Archive
Draft --> Deleted : Delete
Rejected --> Draft : Revise
Published --> Draft : Edit
Archived --> Published : Restore
Deleted --> [*]

note right of Published
  Published state:
  * Visible to public
  * Can be commented
  * Can be shared
end note
@enduml
```

### Feedback Service State
Description: Processes customer feedback with review, escalation, and resolution states.

```plantuml
@startuml
skinparam StateFontStyle bold

[*] --> Submitted : Submit Feedback
Submitted --> UnderReview : Staff Review
UnderReview --> Responded : Staff Response
UnderReview --> Escalated : Escalate Issue
Escalated --> Resolved : Resolve Issue
Responded --> Closed : Close Feedback
Resolved --> Closed : Close Feedback
Closed --> [*]

note right of UnderReview
  Review process:
  * Content check
  * Priority assignment
  * Staff allocation
end note
@enduml
```

## 1. Customer Role

### 1.1 High-Level Design (Customer)

```plantuml
@startuml
skinparam componentStyle uml2

actor Customer
component "Web/Mobile UI" as UI
component "API Gateway" as API
component "Auth Service" as Auth
component "Customer Services" as Services
database "Database" as DB

Customer --> UI : Interact
UI --> API : REST API
API --> Auth : Authenticate
API --> Services : Process
Services --> DB : CRUD

package "Customer Services" {
    component "Account Service" as AS
    component "Appointment Service" as APS
    component "Order Service" as OS
    component "Feedback Service" as FS
    component "Blog Service" as BS
}

note right of Services
  Customer Services include:
  * Account Management
  * Appointment Booking
  * Order Tracking
  * Feedback System
  * Blog Access
end note
@enduml
```

### 1.2 State Diagram (Customer Order)

```plantuml
@startuml
skinparam StateFontStyle bold

[*] --> Created : Create Order
Created --> Scheduled : Book Appointment
Scheduled --> SampleCollected : Collect Sample
SampleCollected --> Testing : Start Test
Testing --> ResultReady : Complete Test
ResultReady --> [*] : View Result

state Created
state Scheduled
state SampleCollected
state Testing
state ResultReady

note right of Created : Customer creates test order
note right of Scheduled : Appointment confirmed
note right of SampleCollected : DNA sample taken
note right of Testing : Lab processing
note right of ResultReady : Results available
@enduml
```

### 1.3 Communication Diagram (Customer)

```plantuml
@startuml
skinparam sequenceMessageAlign center

actor Customer
participant "Web UI" as UI
participant "API Gateway" as API
participant "Auth Service" as Auth
participant "Customer Service" as Service
database "Database" as DB

Customer -> UI : 1. Login/Register
UI -> API : 2. Send Credentials
API -> Auth : 3. Validate
Auth --> API : 4. JWT Token
API --> UI : 5. Auth Success
Customer -> UI : 6. Book Service
UI -> API : 7. Create Order
API -> Service : 8. Process Order
Service -> DB : 9. Save Order
DB --> Service : 10. Confirm
Service --> UI : 11. Order Created
UI --> Customer : 12. Confirmation
@enduml
```

## 2. Staff Role

### 2.1 High-Level Design (Staff)

```plantuml
@startuml
skinparam componentStyle uml2

actor Staff
component "Staff Dashboard" as UI
component "API Gateway" as API
component "Auth Service" as Auth
component "Staff Services" as Services
database "Database" as DB

Staff --> UI : Interact
UI --> API : REST API
API --> Auth : Authenticate
API --> Services : Process
Services --> DB : CRUD

package "Staff Services" {
    component "Sample Management" as SM
    component "Kit Management" as KM
    component "Test Processing" as TP
    component "Report Generation" as RG
}

note right of Services
  Staff Services include:
  * Sample Processing
  * Kit Inventory
  * Test Management
  * Report Creation
end note
@enduml
```

### 2.2 State Diagram (Sample Processing)

```plantuml
@startuml
skinparam StateFontStyle bold

[*] --> Received : Receive Sample
Received --> Validated : Validate Sample
Validated --> Processing : Start Processing
Processing --> Analyzed : Analyze Results
Analyzed --> Reported : Generate Report
Reported --> [*] : Complete Process

state Received
state Validated
state Processing
state Analyzed
state Reported

note right of Received : Sample check-in
note right of Validated : Quality check
note right of Processing : Lab work
note right of Analyzed : Result analysis
note right of Reported : Report ready
@enduml
```

### 2.3 Communication Diagram (Staff)

```plantuml
@startuml
skinparam sequenceMessageAlign center

actor Staff
participant "Staff Dashboard" as UI
participant "API Gateway" as API
participant "Lab Service" as Lab
participant "Report Service" as Report
database "Database" as DB

Staff -> UI : 1. Process Sample
UI -> API : 2. Update Status
API -> Lab : 3. Lab Processing
Lab -> DB : 4. Save Results
Lab -> Report : 5. Generate Report
Report -> DB : 6. Save Report
DB --> UI : 7. Update Status
UI --> Staff : 8. Confirmation
@enduml
```

## 3. Admin Role

### 3.1 High-Level Design (Admin)

```plantuml
@startuml
skinparam componentStyle uml2

actor Admin
component "Admin Dashboard" as UI
component "API Gateway" as API
component "Auth Service" as Auth
component "Admin Services" as Services
database "Database" as DB

Admin --> UI : Interact
UI --> API : REST API
API --> Auth : Authenticate
API --> Services : Process
Services --> DB : CRUD

package "Admin Services" {
    component "User Management" as UM
    component "Service Management" as SM
    component "System Config" as SC
    component "Analytics" as AN
}

note right of Services
  Admin Services include:
  * User Administration
  * Service Configuration
  * System Management
  * Analytics & Reports
end note
@enduml
```

### 3.2 State Diagram (System Management)

```plantuml
@startuml
skinparam StateFontStyle bold

[*] --> Monitoring : Start Monitoring
Monitoring --> Analyzing : Collect Metrics
Analyzing --> Updating : Review Performance
Updating --> Deploying : Make Changes
Deploying --> Monitoring : Continue Monitoring

state Monitoring
state Analyzing
state Updating
state Deploying

note right of Monitoring : System health check
note right of Analyzing : Performance analysis
note right of Updating : System updates
note right of Deploying : Deploy changes
@enduml
```

### 3.3 Communication Diagram (Admin)

```plantuml
@startuml
skinparam sequenceMessageAlign center

actor Admin
participant "Admin Dashboard" as UI
participant "API Gateway" as API
participant "System Service" as System
participant "Analytics Service" as Analytics
database "Database" as DB

Admin -> UI : 1. Access Dashboard
UI -> API : 2. Request Analytics
API -> System : 3. Get System Status
System -> DB : 4. Fetch Metrics
DB --> Analytics : 5. Process Data
Analytics --> UI : 6. Display Results
UI --> Admin : 7. Show Dashboard
@enduml
```

## 4. Integrated System Communication

```plantuml
@startuml
skinparam componentStyle uml2

actor Customer
actor Staff
actor Admin

package "Frontend Layer" {
    component "Customer Portal" as CP
    component "Staff Dashboard" as SD
    component "Admin Console" as AC
}

package "API Gateway" {
    component "Authentication" as Auth
    component "Authorization" as Authz
    component "API Routes" as Routes
}

package "Service Layer" {
    component "Customer Services" as CS
    component "Staff Services" as SS
    component "Admin Services" as AS
    component "Shared Services" as SHS
}

package "Data Layer" {
    database "User DB" as UDB
    database "Test DB" as TDB
    database "System DB" as SDB
}

Customer --> CP
Staff --> SD
Admin --> AC

CP --> Auth
SD --> Auth
AC --> Auth

Auth --> Authz
Authz --> Routes

Routes --> CS
Routes --> SS
Routes --> AS
Routes --> SHS

CS --> UDB
CS --> TDB
SS --> TDB
SS --> SDB
AS --> UDB
AS --> TDB
AS --> SDB
SHS --> UDB

note right of Auth
  Security Layer:
  * JWT Authentication
  * Role-based Access
  * Session Management
end note

note right of Routes
  API Features:
  * Rate Limiting
  * Logging
  * Monitoring
end note

note right of SHS
  Shared Features:
  * Notifications
  * File Storage
  * Logging
end note
@enduml
```

## 5. Detailed Sequence Diagrams

### 5.1 Customer DNA Test Order Flow

```plantuml
@startuml
actor Customer
participant "Web Portal" as Portal
participant "API Gateway" as Gateway
participant "Auth Service" as Auth
participant "Order Service" as Order
participant "Appointment Service" as Appt
participant "Sample Service" as Sample
participant "Test Service" as Test
participant "Report Service" as Report
database "Database" as DB

Customer -> Portal : 1. Login
Portal -> Gateway : 2. Send Credentials
Gateway -> Auth : 3. Authenticate
Auth -> DB : 4. Verify Credentials
DB --> Auth : 5. Return User Data
Auth --> Gateway : 6. Generate JWT Token
Gateway --> Portal : 7. Return Token
Portal --> Customer : 8. Show Dashboard

Customer -> Portal : 9. Create Test Order
Portal -> Gateway : 10. Submit Order Request
Gateway -> Auth : 11. Validate Token
Auth --> Gateway : 12. Token Valid
Gateway -> Order : 13. Create Order
Order -> DB : 14. Save Order
DB --> Order : 15. Order Created
Order -> Appt : 16. Schedule Appointment
Appt -> DB : 17. Save Appointment
DB --> Appt : 18. Appointment Confirmed
Appt --> Order : 19. Return Appointment Details
Order --> Gateway : 20. Return Order Status
Gateway --> Portal : 21. Show Order Confirmation
Portal --> Customer : 22. Display Order & Appointment Details

== Sample Collection Day ==

Customer -> Portal : 23. Arrive for Appointment
Portal -> Gateway : 24. Update Appointment Status
Gateway -> Appt : 25. Start Appointment
Appt -> Sample : 26. Register Sample
Sample -> DB : 27. Save Sample Data
DB --> Sample : 28. Sample Registered
Sample --> Appt : 29. Sample Ready
Appt -> Test : 30. Start Testing Process
Test -> DB : 31. Update Test Status
DB --> Test : 32. Status Updated
Test --> Gateway : 33. Return Status
Gateway --> Portal : 34. Show Processing Status
Portal --> Customer : 35. Display "Sample Collected"

== Test Processing ==

Test -> Sample : 36. Process Sample
Sample -> Test : 37. Analysis Complete
Test -> Report : 38. Generate Report
Report -> DB : 39. Save Report
DB --> Report : 40. Report Saved
Report --> Test : 41. Report Ready
Test --> Gateway : 42. Test Complete
Gateway --> Portal : 43. Results Ready
Portal --> Customer : 44. Notify Results Available

@enduml

### 5.2 Staff Sample Processing Flow

```plantuml
@startuml
actor "Lab Staff" as Staff
participant "Staff Dashboard" as Dashboard
participant "API Gateway" as Gateway
participant "Auth Service" as Auth
participant "Sample Service" as Sample
participant "Test Service" as Test
participant "Kit Service" as Kit
participant "Report Service" as Report
database "Database" as DB

Staff -> Dashboard : 1. Login
Dashboard -> Gateway : 2. Send Credentials
Gateway -> Auth : 3. Authenticate
Auth -> DB : 4. Verify Staff Credentials
DB --> Auth : 5. Return Staff Data
Auth --> Gateway : 6. Generate JWT Token
Gateway --> Dashboard : 7. Return Token
Dashboard --> Staff : 8. Show Lab Dashboard

Staff -> Dashboard : 9. Scan Sample Barcode
Dashboard -> Gateway : 10. Get Sample Info
Gateway -> Sample : 11. Retrieve Sample Data
Sample -> DB : 12. Query Sample
DB --> Sample : 13. Return Sample Details
Sample --> Gateway : 14. Sample Information
Gateway --> Dashboard : 15. Display Sample Details

Staff -> Dashboard : 16. Start Processing
Dashboard -> Gateway : 17. Update Sample Status
Gateway -> Sample : 18. Begin Processing
Sample -> Kit : 19. Assign Test Kit
Kit -> DB : 20. Update Kit Status
DB --> Kit : 21. Kit Assigned
Kit --> Sample : 22. Kit Ready
Sample -> Test : 23. Start Analysis
Test -> DB : 24. Update Test Status
DB --> Test : 25. Status Updated

== Analysis Process ==

Test -> Sample : 26. Extract DNA
Sample -> Test : 27. DNA Ready
Test -> Sample : 28. Run Tests
Sample -> Test : 29. Tests Complete
Test -> Report : 30. Generate Results
Report -> DB : 31. Save Results
DB --> Report : 32. Results Saved
Report --> Test : 33. Report Generated
Test --> Gateway : 34. Processing Complete
Gateway --> Dashboard : 35. Update Status
Dashboard --> Staff : 36. Show Completion

@enduml

### 5.3 Admin System Management Flow

```plantuml
@startuml
actor Admin
participant "Admin Console" as Console
participant "API Gateway" as Gateway
participant "Auth Service" as Auth
participant "User Service" as User
participant "System Service" as System
participant "Analytics Service" as Analytics
database "Database" as DB

Admin -> Console : 1. Login
Console -> Gateway : 2. Send Admin Credentials
Gateway -> Auth : 3. Authenticate
Auth -> DB : 4. Verify Admin Access
DB --> Auth : 5. Return Admin Data
Auth --> Gateway : 6. Generate Admin Token
Gateway --> Console : 7. Return Token
Console --> Admin : 8. Show Admin Dashboard

== System Monitoring ==

Admin -> Console : 9. Request System Status
Console -> Gateway : 10. Get System Metrics
Gateway -> System : 11. Fetch Metrics
System -> DB : 12. Query Performance Data
DB --> System : 13. Return Metrics
System -> Analytics : 14. Process Metrics
Analytics --> System : 15. Analysis Results
System --> Gateway : 16. System Status
Gateway --> Console : 17. Display Metrics
Console --> Admin : 18. Show System Health

== User Management ==

Admin -> Console : 19. View User Reports
Console -> Gateway : 20. Request User Data
Gateway -> User : 21. Get User Statistics
User -> DB : 22. Query User Data
DB --> User : 23. Return User Stats
User -> Analytics : 24. Generate Reports
Analytics --> User : 25. User Analysis
User --> Gateway : 26. User Reports
Gateway --> Console : 27. Display Reports
Console --> Admin : 28. Show User Dashboard

== System Updates ==

Admin -> Console : 29. Initialize Update
Console -> Gateway : 30. Request Update
Gateway -> System : 31. Start Update Process
System -> DB : 32. Backup Data
DB --> System : 33. Backup Complete
System -> System : 34. Apply Updates
System -> DB : 35. Update Configs
DB --> System : 36. Configs Updated
System --> Gateway : 37. Update Complete
Gateway --> Console : 38. Update Status
Console --> Admin : 39. Show Success

@enduml
```

## Chi tiết Luồng Xử Lý

### 1. Customer Flow

- Đăng nhập/Đăng ký qua Customer Portal
- Xác thực thông qua JWT
- Truy cập các dịch vụ khách hàng
- Tương tác với database thông qua Customer Services

### 2. Staff Flow

- Đăng nhập vào Staff Dashboard
- Xử lý mẫu và cập nhật trạng thái
- Tạo báo cáo và quản lý kit
- Tương tác với Test DB và System DB

### 3. Admin Flow

- Quản lý toàn bộ hệ thống qua Admin Console
- Giám sát và phân tích hệ thống
- Cấu hình và quản lý người dùng
- Truy cập đầy đủ vào tất cả databases

### 4. Security & Integration

- Xác thực tập trung qua API Gateway
- Phân quyền dựa trên role
- Logging và monitoring tập trung
- Shared services cho các chức năng chung

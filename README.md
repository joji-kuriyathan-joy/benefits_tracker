### **Product Documentation for Benefits Tracker Application**
_Version: 1.0_
 _Author: Joji Kuriyathan Joy_
 _Date: March 2025_

### Project Scenario: Prototype a new Software system to document and store details of Participant applying for number of related benefits. 
# **📖 Table of Contents**
1. [﻿1️⃣ Introduction]
2. [﻿2️⃣ Project Overview]
3. [﻿3️⃣ System Architecture]
4. [﻿4️⃣ Technologies Used]
5. [﻿5️⃣ Core Features]
6. [﻿6️⃣ API Endpoints]
7. [﻿7️⃣ Database Schema]
8. [﻿8️⃣ Validation & Business Rules]
9. [﻿9️⃣ Testing Strategy]
10. [﻿🔐 10️⃣ Security Considerations]
11. [﻿1️⃣1️⃣ Future Enhancements]
12. [﻿1️⃣2️⃣ Conclusion]


# **1️⃣ Introduction**
The **Benefits Tracker Application** is a **Spring Boot**-based system designed to manage participants applying for benefits. It ensures secure storage, validation, and tracking of user details. The system follows a **three-layered architecture** comprising:

✅ **Presentation Layer**: RESTful APIs (Future enhancement – Web UI)
 ✅ **Business Layer**: Handles logic, validations, and tracking mechanisms
 ✅ **Data Layer**: Manages data persistence using JPA/Hibernate

The system also features **audit tracking**, allowing modifications to be stored as historical evidence.

---

# **2️⃣ Project Overview**
## **📌 Objective**
The **Benefits Tracker Application** is built to:

- Store and manage participant details.
- Validate data inputs (e.g., NINO, emails, UK postcodes).
- Track changes in participant data (audit evidence).
- Provide RESTful APIs for interaction.
## **📌 Scope**
- **In Scope**:
    - RESTful API for participant management
    - Validation & business rules enforcement
    - Data persistence using a relational database
    - Change tracking system (audit logs)
- **Out of Scope** (Future Enhancements):
    - UI implementation (React/Angular)
    - Payment processing integration
---

# **3️⃣ System Architecture**
The system follows a **three-tiered architecture**:

📌 **1. Presentation Layer**

- REST API built using **Spring Boot**
- JSON-based request/response model
- API endpoints secured using validation
📌 **2. Business Layer**

- Implements validation, processing, and audit tracking
- Uses **Spring Service Layer** to manage business logic
📌 **3. Data Layer**

- Uses **Spring Data JPA** for database operations
- Stores participant details in a **relational database**
- Implements **change tracking** via `Evidence`  entity

| Presentation Layer | Business Layer | Data Layer |
| ----- | ----- | ----- |
| User Interface (HTML, CSS, JS) | Business Logic (Java) | Database management (SQL) |

**Visual Representation**

- [﻿High-Level Application Architecture (Flowchart)](https://app.eraser.io/workspace/G1SXJ3oGTBdB2UejpmBi?elements=ewHJXG9XShYfZMm69TAb2w) 
- [﻿Participant and Evidence Data Flow Chart](https://app.eraser.io/workspace/G1SXJ3oGTBdB2UejpmBi?elements=yfoVYDH1SYCUvTs5Esaq6w) 
---

# **4️⃣ Technologies Used**
| **Technology** | **Purpose** |
| ----- | ----- |
| **Java 21** | Core backend development |
| **Spring Boot** | Application framework |
| **Spring Data JPA** | ORM and database operations |
| **Postgres SQL** | Database for storing participant details |
| **Lombok** | Reduces boilerplate code |
| **JUnit & Mockito** | Testing framework |
| **Swagger (Out of Scope)** | API documentation |
| **Postman** | API testing |
Why SPRING BOOT ?? Because of **Simplified Configuration, Embedded Server**, **Reduced Boilerplate Code, Production Readiness, Microservices Support, Faster Development, Ease of Use, Dependency Management**



Spring Initializer : Create spring boot project template , with needed dependency to kick start the development. 

[﻿start.spring.io/](https://start.spring.io/) 



![image.png](https://eraser.imgix.net/workspaces/G1SXJ3oGTBdB2UejpmBi/qwfEexGOl0hhdnRMfZpTm0u0uqY2/s4xQlkein5CPjfgJStr9n.png?ixlib=js-3.7.0 "image.png")



IDE : InteliJ Community Edition

---

# **5️⃣ Core Features**
### **✅ Participant Management**
- Register, update, retrieve, and delete participant records
- Secure storage of personal details
### **✅ Data Validation**
- Validates NINO, email, phone numbers, and UK postcodes
### **✅ Evidence Tracking (Audit Logs)**
- Changes to participant data are recorded
- Ensures historical records for compliance
### **✅ API-Based Communication**
- Fully RESTful service
- Supports JSON request/response
---

# **6️⃣ API Endpoints**
### **📌 Participant APIs**
| HTTP Method | Endpoint | Description |
| ----- | ----- | ----- |
| **POST** |  | Register a new participant |
| **GET** |  | Retrieve a participant by ID |
| **GET** |  | Retrieve all participants |
| **PUT** |  | Update participant details |
| **DELETE (Out of Scope)** |  | Delete a participant |
### **📌 Evidence APIs (Audit Tracking)**
| HTTP Method | Endpoint | Description |
| ----- | ----- | ----- |
| **GET** |  | Retrieve change history for a participant |
---

# **7️⃣ Database Schema **
[﻿View on canvas](https://app.eraser.io/workspace/G1SXJ3oGTBdB2UejpmBi?elements=pRf8qIAbqiMviMQ3BDguZA) 

### **📌 Entity: **`**Participant**` 
| Column | Type | Constraints |
| ----- | ----- | ----- |
| id | UUID | Primary Key |
| registrationDate | Date (Auto-generated) | Not Null |
| dateOfBirth | Date | Not Null |
| firstName | String | Not Null |
| surName | String | Not Null |
| primaryAddress | String | Not Null |
| primaryBankAccount | String | Not Null |
| nationalInsuranceNumber | String | Optional |
| primaryTelephone | String | Optional |
| primaryEmailAddress | String | Optional |
### **📌 Entity: **`**Evidence**`** (Audit Logs)**
| Column | Type | Constraints |
| ----- | ----- | ----- |
| id | UUID | Primary Key |
| participant_id | UUID | Foreign Key (Participant) |
| fieldName | String | Tracked Field |
| oldValue | String | Previous Value |
| newValue | String | Updated Value |
| changeTime | Timestamp | Auto-generated |
---

# **8️⃣ Validation & Business Rules**
| Field    | Validation |
| ----- | ----- |
| **First Name & Surname  **[﻿Retrieve Participant’s Active First Name & Surname](https://app.eraser.io/workspace/G1SXJ3oGTBdB2UejpmBi?elements=cr2P_XGhkEv_N8F2tKQMlA)** ** | Required, Only one active name |
| **National Insurance Number  **[﻿National Insurance Number (NINO) Validation Flow](https://app.eraser.io/workspace/G1SXJ3oGTBdB2UejpmBi?elements=8_D-RvgERgc2qlNtduDR0w)** ** | Valid UK NINO format |
| **Email  **[﻿Email  Validation Flow](https://app.eraser.io/workspace/G1SXJ3oGTBdB2UejpmBi?elements=pylxlxhN8w7xrRai7DWlgQ)** ** | <p>Standard email format (</p><p> required)</p> |
| **Phone Number  **[﻿Telephone Number Validation](https://app.eraser.io/workspace/G1SXJ3oGTBdB2UejpmBi?elements=r_cvhGks5EbkjFyWj4aEJw)** ** | Must contain only digits, 10-15 length |
| **Primary Address  **[﻿Address (UK Postcode) Validation Flow](https://app.eraser.io/workspace/G1SXJ3oGTBdB2UejpmBi?elements=2Njy8NGRkgcOc0kMX_wnnQ)** ** | Must have a valid UK postcode |
| **Bank Account  **[﻿Participant's Active Bank Account Retrieval](https://app.eraser.io/workspace/G1SXJ3oGTBdB2UejpmBi?elements=8xDaecTghgwmlmHblCv4pA)** ** | Only one active per participant |
---

# **9️⃣ Testing Strategy**
### **✅ Unit Tests (JUnit & Mockito) -> In scope**
- `ParticipantServiceTest`  → Tests business logic
- `EvidenceServiceTest`  → Ensures change tracking works
### **✅ Integration Tests (MockMvc) -> Out of Scope**
- `ParticipantControllerTest`  → Tests API endpoints
### **✅ Edge Case Testing  -> Out of Scope**
- Empty/missing values
- Invalid formats
- Updates with unchanged values
---

# **🔐 10️⃣ Security Considerations**
### **✅ Preventing SQL Injection**
- Uses **Spring Data JPA**, preventing raw SQL execution.
### **✅ Exception Handling**
- Custom exception handling via `@RestControllerAdvice` .
### **✅ Input Validation**
- Ensures all user inputs follow defined rules (e.g., NINO format).
---

# **1️⃣1️⃣ Future Enhancements**
1. **Implement Frontend (React/Angular)**
2. **OAuth2 Authentication & Role-Based Access**
---

# **1️⃣2️⃣ Conclusion**
The **Benefits Tracker Application** provides a **robust, secure, and scalable** solution for managing participant records. The **three-tier architecture**, along with **validation, audit tracking, and structured API design**, ensures long-term maintainability. 🚀


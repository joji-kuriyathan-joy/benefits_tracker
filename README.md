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

- [﻿High-Level Application Architecture (Flowchart)]![image](https://github.com/user-attachments/assets/ad908800-dccc-4d01-9c05-02fe7aea4938)

- [﻿Participant and Evidence Data Flow Chart]![image](https://github.com/user-attachments/assets/b157868f-8ef3-4e25-a055-e3386860480a)

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



![image](https://github.com/user-attachments/assets/6b95c502-c733-41ad-a395-00bc7d49589f)



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
[﻿View on canvas](![image](https://github.com/user-attachments/assets/8d4e66b6-b7a4-4e8d-bd55-02992d5dacb6)
) 

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
| **First Name & Surname  **[﻿Retrieve Participant’s Active First Name & Surname](![image](https://github.com/user-attachments/assets/53157a7f-9bf7-4f76-9783-75cd51cf5a8c)
)** ** | Required, Only one active name |
| **National Insurance Number  **[﻿National Insurance Number (NINO) Validation Flow](![image](https://github.com/user-attachments/assets/3673db29-076b-4199-a6a3-d5aa16000366)
)** ** | Valid UK NINO format |
| **Email  **[﻿Email  Validation Flow](![image](https://github.com/user-attachments/assets/090c52a9-7427-4adb-bf22-c1382a5a61f1)
)** ** | <p>Standard email format (</p><p> required)</p> |
| **Phone Number  **[﻿Telephone Number Validation](![image](https://github.com/user-attachments/assets/eecd53b0-544b-47b4-8c24-c7c464b16bfe)
)** ** | Must contain only digits, 10-15 length |
| **Primary Address  **[﻿Address (UK Postcode) Validation Flow](![image](https://github.com/user-attachments/assets/e2052800-19ef-4351-8fee-42dc5ee4a3dd)
)** ** | Must have a valid UK postcode |
| **Bank Account  **[﻿Participant's Active Bank Account Retrieval](![image](https://github.com/user-attachments/assets/e3252a63-9428-4d10-9199-d1875323017c)
)** ** | Only one active per participant |

Bussiness Logic Flow : 
![image](https://github.com/user-attachments/assets/7671f75c-7a89-456e-827f-589b5fc0522f)
![image](https://github.com/user-attachments/assets/f225d2ab-5ec2-4670-80af-e0b6d9b62918)


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


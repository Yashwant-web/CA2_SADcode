# CA2_SADcode
**Secure Application Development - Project**

This repository contains a secure application development project focused on implementing security controls, identifying vulnerabilities, and applying secure coding practices.

**Project Overview**

The goal of this project is to develop an application with strong security practices by addressing key security issues such as SQL injection, Cross-Site Scripting (XSS), session management, and securing credentials. The project also includes security testing using state-of-the-art tools and techniques.

**Table of Contents**
- Installation

- Tools and Technologies

- Security Testing

- Code Review

- Proposed Solutions

- Contributors

**Installation**
Prerequisites:
Java 17 or higher

SonarScanner

OWASP ZAP

**Steps:**
Clone the repository: 

    git clone https://github.com/yourusername/secure-app.git
    
    cd secure-app

Set up SonarScanner:

Download and configure SonarScanner for your operating system.

Set the sonar.token property in the sonar-project.properties file or set the SONAR_TOKEN environment variable.

Run SonarScanner:

sonar-scanner

Set up and configure OWASP ZAP:

Install OWASP ZAP.

Configure and run OWASP ZAP to test the application for vulnerabilities like XSS and SQL injection.

Tools and Technologies
Java: Used as the primary programming language for developing secure application logic.

SonarScanner: A static analysis tool used to identify code quality issues and security vulnerabilities.

OWASP ZAP: A dynamic testing tool used to identify security vulnerabilities such as XSS, SQL injection, etc.

Spring Security: Used for implementing secure session management and authentication mechanisms.

**Security Testing**
**Testing Tools:**
SonarScanner: Used for Static Application Security Testing (SAST) to identify vulnerabilities like hardcoded credentials and SQL injection points by analyzing the source code.

OWASP ZAP: Used for Dynamic Application Security Testing (DAST) to scan the running application for vulnerabilities like Cross-Site Scripting (XSS) and weak session management.

Manual Penetration Testing: Conducted by security researchers to manually inspect the code and simulate real-world attacks to identify vulnerabilities not easily caught by automated tools.

**Key Findings:**
Hardcoded Credentials: Database credentials were found hardcoded in the source code, posing a security risk. This was mitigated by moving credentials to a secure configuration file and adding it to .gitignore to avoid exposure in version control systems.

SQL Injection Vulnerabilities: Several instances of user input being concatenated directly into SQL queries were found. These were mitigated by using prepared statements or parameterized queries.

XSS Vulnerabilities: Input sanitization issues were identified, leading to the risk of XSS attacks. Solutions were implemented using Java's OWASP Java Encoder and JSF output encoding.

Session Management: Weak session management practices were found, including improper cookie handling and lack of session expiration. These were improved using Spring Security to implement secure cookies, session expiration, and multi-factor authentication.
**Code Review**
A detailed code review was conducted to assess the application’s adherence to secure coding practices. The main areas of concern were:

Code Duplication: Duplicated logic across the codebase was refactored to consolidate common functionality into reusable methods, improving maintainability and consistency of security practices.

Hardcoded Credentials: Credentials were identified in the code, and as part of the fix, sensitive data was moved to configuration files.
**Proposed Solutions**
Based on the findings from security testing and the code review, the following solutions were proposed:

SQL Injection Prevention: Use prepared statements or parameterized queries to ensure that user inputs are handled safely.

XSS Protection: Implement input sanitization and output encoding to mitigate XSS vulnerabilities.

Session Management: Enhance session security by using secure cookies, session expiration, and implementing multi-factor authentication (MFA).

Securing Credentials: Move sensitive data (like database credentials) into configuration files, and exclude them from version control using .gitignore.

Code Duplication: Refactor the code to eliminate duplication and improve maintainability and consistency of security practices.

**Contributors**
Yashwant Salukhe - Developer and Project Lead

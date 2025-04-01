# Crime Watchers

**Crime Watchers** is a community-focused web application built to help residents stay informed about local criminal activity. It allows users to post and browse incidents by borough, access historical data, and engage with others through comments — all with the goal of fostering safer neighborhoods.

---

## 🎥 Demo

👉 
![crimeWatchers](https://user-images.githubusercontent.com/90943184/212566731-8d182c15-8a0d-4b27-b918-dfc968963196.gif)

---

## 🚀 Features

- **Report Criminal Activity** – Submit incident reports to raise community awareness.
- **Browse by Borough** – Filter incidents based on location for more relevant updates.
- **View Historical Data** – See past reports to understand trends over time.
- **Comment on Reports** – Discuss and share insights with other users.

---

## 🔧 Data Source

This project uses **mock crime data** generated through local scripts. These scripts populate a **DynamoDB** table, which the application regularly queries to simulate a live data experience. While not connected to real-time feeds, this setup allowed for full-stack integration and realistic user interaction during development.

---

## 🔐 In Progress

- User Authentication
- External API integrations with official crime databases

---

## 🛠️ Tech Stack

- **Backend:** Java, Spring Boot, Dagger, AWS Lambda  
- **Frontend:** JavaScript, HTML, CSS  
- **Database:** DynamoDB (mock data)

---

## 📌 Notes

This project was designed with the intent to demonstrate:
- Full-stack web development
- Integration with AWS services (Lambda & DynamoDB)
- Mock data simulation in lieu of a live API feed




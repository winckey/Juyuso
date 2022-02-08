// Give the service worker access to Firebase Messaging.
// Note that you can only use Firebase Messaging here, other Firebase libraries
// are not available in the service worker.

// importScripts('https://www.gstatic.com/firebasejs/9.6.6/firebase-app.js');
// importScripts('https://www.gstatic.com/firebasejs/9.6.6/firebase-messaging.js');

// import { initializeApp } from "firebase/app";
// import { getMessaging } from "firebase/messaging/sw";
// import { onBackgroundMessage } from "firebase/messaging/sw";

// // Initialize the Firebase app in the service worker by passing the generated config
// const app = initializeApp({
//   apiKey: "AIzaSyDLDx9b0c8DplnM14-WDEErlHHq5N1Xl6Y",
//   authDomain: "juyuso-ssafy.firebaseapp.com",
//   projectId: "juyuso-ssafy",
//   storageBucket: "juyuso-ssafy.appspot.com",
//   messagingSenderId: "827632160252",
//   appId: "1:827632160252:web:bb51a70eefbaf4f9c9b679"
// });

// // Retrieve an instance of Firebase Messaging so that it can handle background
// // messages.
// const messaging = getMessaging(app);

// onBackgroundMessage(messaging, (payload) => {
//   console.log('[firebase-messaging-sw.js] Received background message ', payload);
//   // Customize notification here
//   // const notificationTitle = 'Background Message Title';
//   // const notificationOptions = {
//   //   body: 'Background Message body.',
//   //   icon: '/firebase-logo.png'
//   // };

//   const notificationTitle = payload.notification.title;
//   const notificationOptions = {
//     body: payload.notification.body,
//   };

//   self.registration.showNotification(notificationTitle,
//     notificationOptions);
// });

importScripts('https://www.gstatic.com/firebasejs/9.6.6/firebase-app-compat.js');
importScripts('https://www.gstatic.com/firebasejs/9.6.6/firebase-messaging-compat.js');
const firebaseConfig = {
  apiKey: "AIzaSyDLDx9b0c8DplnM14-WDEErlHHq5N1Xl6Y",
  authDomain: "juyuso-ssafy.firebaseapp.com",
  projectId: "juyuso-ssafy",
  storageBucket: "juyuso-ssafy.appspot.com",
  messagingSenderId: "827632160252",
  appId: "1:827632160252:web:bb51a70eefbaf4f9c9b679"
}
firebase.initializeApp(firebaseConfig)
firebase.messaging()
const messaging  = firebase.messaging()
messaging.onBackgroundMessage((payload) => {
    console.log("tesing sevice worker")
    // Customize notification here
    const notificationTitle = payload.notification.title;
    const notificationOptions = {
      body: payload.notification.body,
    };
  
    self.registration.showNotification(notificationTitle,
      notificationOptions);
  });
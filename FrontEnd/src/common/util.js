import { getMessaging, getToken } from 'firebase/messaging'

export const phoneFormatter = num => 
  num.replace(/[^0-9]/, '')
    .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`)

export async function getFcmToken() {
  const PUBLIC_VAPID_KEY = 'BNEXCWddmnyA6pokCD8W5cGv9JBI6gA2IeDlf7RbP9VzVoXN23r8J-ULN-bdkAyS6gB0aVw7DUNokhdSUuNfdmU';
  const messaging = getMessaging();
  
  const token = await getToken(messaging, { vapidKey: PUBLIC_VAPID_KEY })
              .then(token => token)

  return token;
}

export default {
  phoneFormatter,
  getFcmToken
}
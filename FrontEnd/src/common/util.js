export const phoneFormatter = num => 
  num.replace(/[^0-9]/, '')
    .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`)

export default {
  phoneFormatter
}
import { createGlobalStyle } from 'styled-components/macro'

export default createGlobalStyle`
  :root {
    --gray: #6c757d;
    --gray-dark: #343a40;
    --accent: #e9967a;

    --size-xs: 4px;
    --size-s: 8px;
    --size-m: 12px;
    --size-l: 16px;
    --size-xl: 24px;
    --size-xxl: 32px;
  }

  * {
    box-sizing: border-box;
  }

  html, body {
    margin: 0;
    font-family: Helvetica, serif;
  }
`

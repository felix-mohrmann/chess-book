import { createGlobalStyle } from 'styled-components/macro'

export default createGlobalStyle`
  :root {
    --gray: #6c757d;
    --gray-dark: #343a40;
  }

  * {
    box-sizing: border-box;
  }

  html, body {
    margin: 0;
    font-family: Helvetica, serif;
  }
`

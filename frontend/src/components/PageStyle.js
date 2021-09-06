import styled from 'styled-components/macro'

export default styled.div`
  position: fixed;
  width: 100%;
  height: 100%;
  background-color: var(--gray);

  display: grid;
  grid-template-rows: min-content 1fr min-content;
  place-items: center;
`

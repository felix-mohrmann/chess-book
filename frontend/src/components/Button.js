import styled from 'styled-components'
import { css } from 'styled-components'

export default styled.button`
  padding: var(--size-m);
  background: var(--accent);
  border: 1px solid var(--accent);
  color: var(--neutral-light);
  font-size: 1em;
  border-radius: var(--size-s);

  :disabled {
    border-color: var(--gray-dark);
    background: var(--gray-dark);
    color: var(--neutral-light);
  }

  a:hover {
    background-color: var(--neutral-light);
    color: var(--accent);
  }

  ${props =>
    props.secondary &&
    css`
      background: none;
      color: var(--accent);
      border: 1px solid var(--accent);

      :disabled {
        border-color: var(--gray-dark);
        background: none;
        color: var(--gray-dark);
      }
    `}
`

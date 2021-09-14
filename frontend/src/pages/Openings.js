import OpeningCard from '../components/OpeningCard'
import Navbar from '../components/Navbar'
import PageStyle from '../components/PageStyle'
import styled from 'styled-components/macro'

export default function Openings() {
  return (
    <PageStyle>
      <Navbar />
      <Wrapper>
        <h1>White</h1>
        <OpeningCard />
        <h1>Black</h1>
      </Wrapper>
    </PageStyle>
  )
}

const Wrapper = styled.div`
  display: grid;
`

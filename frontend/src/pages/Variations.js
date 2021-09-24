import Navbar from '../components/Navbar'
import PageStyle from '../components/PageStyle'
import Chessboard from 'chessboardjsx'
import { Component } from 'react'
import PropTypes from 'prop-types'
import Button from '@mui/material/Button'
import { ButtonGroup } from '@mui/material'
import BookmarkOutlinedIcon from '@mui/icons-material/BookmarkOutlined'
import DeleteIcon from '@mui/icons-material/Delete'
import styled from 'styled-components/macro'
import { saveVariation } from '../service/chess-book-api'

class HumanVsHuman extends Component {
  static propTypes = { children: PropTypes.func }

  state = {
    fen: 'start',
    //square with the currently clicked piece
    pieceSquare: '',
    //currently clicked square
    square: '',
    history: [],
  }

  componentDidMount() {
    const Chess = require('chess.js')
    this.game = new Chess()
  }

  onDrop = ({ sourceSquare, targetSquare }) => {
    let move = this.game.move({
      from: sourceSquare,
      to: targetSquare,
      promotion: 'q',
    })

    //illegal move
    if (move === null) return
    this.setState({
      fen: this.game.fen(),
      history: this.game.history(),
    })
  }

  onSquareClick = square => {
    this.setState({
      pieceSquare: square,
    })

    let move = this.game.move({
      from: this.state.pieceSquare,
      to: square,
      promotion: 'q',
    })

    //illegal move
    if (move === null) return
    this.setState({
      fen: this.game.fen(),
      history: this.game.history(),
      pieceSquare: '',
    })
  }

  resetBoard = () => {
    this.game.reset()
    this.setState({
      fen: 'start',
      pieceSquare: '',
      square: '',
      history: [],
    })
  }

  saveVariation = () => {
    saveVariation(this.state.history).catch(error => console.error(error))
  }

  render() {
    const { fen, history } = this.state
    console.log(history, fen)
    return this.props.children({
      position: fen,
      onDrop: this.onDrop,
      onSquareClick: this.onSquareClick,
      resetBoard: this.resetBoard,
      saveVariation: this.saveVariation,
    })
  }
}

export default function Variations() {
  return (
    <PageStyle>
      <Navbar />
      <div>
        <HumanVsHuman>
          {({ position, onDrop, onSquareClick, resetBoard, saveVariation }) => (
            <Wrapper>
              <div class="Board">
                <Chessboard
                  position={position}
                  onDrop={onDrop}
                  onSquareClick={onSquareClick}
                />
              </div>
              <div className="Buttons">
                <ButtonGroup orientation="vertical">
                  <Button
                    startIcon={<BookmarkOutlinedIcon />}
                    variant="contained"
                    color="secondary"
                    onClick={saveVariation}
                  >
                    Save Variation
                  </Button>
                  <Button
                    startIcon={<DeleteIcon />}
                    variant="contained"
                    onClick={resetBoard}
                  >
                    Reset Board
                  </Button>
                </ButtonGroup>
              </div>
            </Wrapper>
          )}
        </HumanVsHuman>
      </div>
    </PageStyle>
  )
}

const Wrapper = styled.div`
  display: grid;
  grid-template: repeat(3, 1fr) / 1.3fr 0.7fr;
  grid-template-areas:
    'Board .'
    'Board Buttons'
    'Board .';

  .Board {
    grid-area: Board;
  }

  .Buttons {
    grid-area: Buttons;
    align-self: center;
    justify-self: center;
  }
`

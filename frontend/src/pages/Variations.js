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
      history: this.game.history({ verbose: true }),
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
      history: this.game.history({ verbose: true }),
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

  render() {
    const { fen, history } = this.state
    console.log(history, fen)
    return this.props.children({
      position: fen,
      onDrop: this.onDrop,
      onSquareClick: this.onSquareClick,
      resetBoard: this.resetBoard,
    })
  }
}

export default function Variations() {
  return (
    <PageStyle>
      <Navbar />
      <div>
        <HumanVsHuman>
          {({ position, onDrop, onSquareClick, resetBoard }) => (
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

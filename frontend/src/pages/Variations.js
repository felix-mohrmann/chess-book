import Navbar from '../components/Navbar'
import PageStyle from '../components/PageStyle'
import Chessboard from 'chessboardjsx'
import { Component, useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import Button from '@mui/material/Button'
import { ButtonGroup, TextField } from '@mui/material'
import BookmarkOutlinedIcon from '@mui/icons-material/BookmarkOutlined'
import DeleteIcon from '@mui/icons-material/Delete'
import SwapVertIcon from '@mui/icons-material/SwapVert'
import styled from 'styled-components/macro'
import { getVariations, saveVariation } from '../service/chess-book-api'
import Variation from '../components/Variation'

class HumanVsHuman extends Component {
  static propTypes = { children: PropTypes.func }

  state = {
    orientation: 'white',
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

  flipOrientation = () => {
    if (this.state.orientation === 'white') {
      this.setState({
        orientation: 'black',
      })
    } else {
      this.setState({
        orientation: 'white',
      })
    }
  }

  resetBoard = () => {
    this.game.reset()
    this.setState({
      fen: 'start',
      orientation: 'white',
      pieceSquare: '',
      square: '',
      history: [],
    })
  }

  saveVariation = name => {
    saveVariation(this.state.history, name, this.state.orientation).catch(
      error => console.error(error)
    )
  }

  showVariation = variation => {
    this.game.reset()
    this.setState({
      fen: 'start',
      orientation: variation.orientation,
      pieceSquare: '',
      square: '',
      history: [],
    })

    for (let i = 0; i < variation.moveArray.length; i++) {
      setTimeout(() => {
        this.game.move(variation.moveArray[i])
        this.setState({
          fen: this.game.fen(),
          history: this.game.history(),
        })
      }, 2000)
    }
  }

  render() {
    const { fen, history, orientation } = this.state
    console.log(history)
    return this.props.children({
      position: fen,
      onDrop: this.onDrop,
      orientation: orientation,
      onSquareClick: this.onSquareClick,
      flipOrientation: this.flipOrientation,
      resetBoard: this.resetBoard,
      saveVariation: this.saveVariation,
      showVariation: this.showVariation,
    })
  }
}

export default function Variations() {
  const [name, setName] = useState()

  const handleChange = event => setName(event.target.value)
  const handleSubmit = event => {
    event.preventDefault()
    updateVariations()
    setName('')
  }

  const [variations, setVariations] = useState()

  const updateVariations = () => {
    setTimeout(() => {
      getVariations()
        .then(setVariations)
        .catch(error => console.error(error))
    }, 500)
  }

  useEffect(() => {
    updateVariations()
  }, [])

  return (
    <PageStyle>
      <Navbar />
      <div>
        <HumanVsHuman>
          {({
            orientation,
            flipOrientation,
            position,
            onDrop,
            onSquareClick,
            resetBoard,
            saveVariation,
            showVariation,
          }) => (
            <Wrapper>
              <div class="Saved">
                {variations &&
                  variations.variants.map(variation => (
                    <Variation
                      variation={variation}
                      update={updateVariations}
                      show={showVariation}
                    />
                  ))}
              </div>
              <div class="Board">
                <Chessboard
                  position={position}
                  orientation={orientation}
                  onDrop={onDrop}
                  onSquareClick={onSquareClick}
                />
              </div>
              <div className="Util">
                <Form onSubmit={handleSubmit}>
                  <TextField
                    fullWidth
                    variant="outlined"
                    color="warning"
                    value={name}
                    label="name"
                    onChange={handleChange}
                  />
                  <ButtonGroupWrap
                    orientation="vertical"
                    fullWidth
                    variant="outlined"
                  >
                    <Button
                      startIcon={<BookmarkOutlinedIcon />}
                      type="submit"
                      onClick={() => saveVariation(name)}
                    >
                      Save
                    </Button>
                    <Button
                      startIcon={<SwapVertIcon />}
                      type="button"
                      onClick={flipOrientation}
                    >
                      Flip
                    </Button>
                    <Button
                      startIcon={<DeleteIcon />}
                      type="button"
                      onClick={resetBoard}
                    >
                      Reset
                    </Button>
                  </ButtonGroupWrap>
                </Form>
              </div>
            </Wrapper>
          )}
        </HumanVsHuman>
      </div>
    </PageStyle>
  )
}

const Form = styled.form``

const ButtonGroupWrap = styled(ButtonGroup)`
  Button {
    background-color: #b48963;
    color: black;
    border: none;
  }

  Button:hover {
    background-color: #efdab5;
    border: none;
  }
`

const Wrapper = styled.div`
  display: grid;
  grid-template: 1fr / 300px min-content 200px;
  grid-template-areas: 'Saved Board Util';

  .Board {
    grid-area: Board;
  }

  .Saved {
    grid-area: Saved;
    border-bottom: var(--accent) 3px solid;
    border-top: var(--accent) 3px solid;
    border-left: var(--accent) 3px solid;
    overflow-y: scroll;
  }

  .Util {
    margin: var(--size-xxl);
    grid-area: Util;
    align-self: center;
    text-align: center;
  }
`

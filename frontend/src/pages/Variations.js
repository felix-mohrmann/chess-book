import Navbar from '../components/Navbar'
import PageStyle from '../components/PageStyle'
import Chessboard from 'chessboardjsx'
import { Component } from 'react'
import PropTypes from 'prop-types'

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

  render() {
    const { fen } = this.state
    return this.props.children({
      position: fen,
      onDrop: this.onDrop,
      onSquareClick: this.onSquareClick,
    })
  }
}

export default function Variations() {
  return (
    <PageStyle>
      <Navbar />
      <HumanVsHuman>
        {({ position, onDrop, onSquareClick }) => (
          <Chessboard
            position={position}
            onDrop={onDrop}
            onSquareClick={onSquareClick}
          />
        )}
      </HumanVsHuman>
    </PageStyle>
  )
}

import { Link } from 'react-router-dom'

export default function OpeningCard({ opening, index, orientation }) {
  return (
    <Link to={`/openings/${opening.name}/${orientation}/${opening.fen}`}>
      <h3>
        {index + 1}. {opening.name} ({opening.totalGames} Games) - WR:{' '}
        {opening.winPercentage}%
      </h3>
    </Link>
  )
}

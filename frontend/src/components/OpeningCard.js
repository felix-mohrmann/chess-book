import { Link } from 'react-router-dom'

export default function OpeningCard({ opening, index }) {
  return (
    <Link to={`/openings/${opening.name}`} opening={opening}>
      <h3>
        {index + 1} {opening.name} ({opening.totalGames} Games) - WR:{' '}
        {opening.winPercentage}%
      </h3>
    </Link>
  )
}

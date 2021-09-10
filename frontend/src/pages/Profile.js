import PageStyle from '../components/PageStyle'
import Navbar from '../components/Navbar'
import { useEffect, useState } from 'react'
import { getProfile } from '../service/lichess-api'

export default function Profile() {
  const [profile, setProfile] = useState()

  useEffect(() => {
    getProfile()
      .then(setProfile)
      .catch(error => console.error(error))
  }, [])

  return (
    <PageStyle>
      <Navbar />
      {profile && (
        <div>
          <h1>Welcome {profile.profile.firstName}</h1>
          <h2>Your current Ratings:</h2>
          <h3>Bullet: {profile.perfs.bullet.rating}</h3>
          <h3>Blitz: {profile.perfs.blitz.rating}</h3>
          <h3>Rapid: {profile.perfs.rapid.rating}</h3>
          <h3>Classical: {profile.perfs.classical.rating}</h3>
        </div>
      )}
    </PageStyle>
  )
}

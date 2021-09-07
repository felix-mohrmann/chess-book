import PageStyle from '../components/PageStyle'
import Navbar from '../components/Navbar'
import { useEffect, useState } from 'react'
import { getProfile } from '../service/lichess-api'

export default function Profile() {
  const [profile, setProfile] = useState()

  useEffect(() => {
    getProfile()
      .then(setProfile)
      .then(profile => console.log(profile))
      .catch(error => console.error(error))
  }, [])

  return (
    <PageStyle>
      <Navbar />
    </PageStyle>
  )
}

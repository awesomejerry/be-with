const oneDay = 24 * 60 * 60 * 1000
const valueCache = {}

const startFrom = new Date('2013-07-16 00:00')

document.addEventListener('DOMContentLoaded', main)

const count = () => {
  const now = new Date()
  const diffDays = Math.round(
    Math.abs((startFrom.getTime() - now.getTime()) / oneDay)
  )
  if (valueCache.diffDays !== diffDays) { document.getElementById('diff-days').innerHTML = diffDays }

  const diffDuration = moment.duration(moment(now).diff(moment(startFrom)))
  const year = diffDuration.years()
  const month = diffDuration.months()
  const day = diffDuration.days()
  const hour = diffDuration.hours()
  const minute = diffDuration.minutes()
  const second = diffDuration.seconds()
  if (valueCache.year !== year) document.getElementById('year').innerHTML = year
  if (valueCache.month !== month) { document.getElementById('month').innerHTML = month }
  if (valueCache.day !== day) document.getElementById('day').innerHTML = day
  if (valueCache.hour !== hour) document.getElementById('hour').innerHTML = hour
  if (valueCache.minute !== minute) { document.getElementById('minute').innerHTML = minute }
  if (valueCache.second !== second) { document.getElementById('second').innerHTML = second }

  valueCache.diffDays = diffDays
  valueCache.year = year
  valueCache.month = month
  valueCache.day = day
  valueCache.hour = hour
  valueCache.minute = minute
  valueCache.second = second
}

function main () {
  count()
  const counting = setInterval(count, 100)
}

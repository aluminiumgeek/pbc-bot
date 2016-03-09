print "Specify range to choose a random number from." and return if ARGV.count < 2

min = ARGV[0].to_i
max = ARGV[1].to_i

if min > max
  print "Left bound should not be greater than right one."
else
  print Random.rand(min..max)
end

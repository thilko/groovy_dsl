import spock.lang.Specification

class DslParserSpec extends Specification{

       def input =  """
                    house {
                        name "induction"

                        room {
                            name "Room 1"
                            bed { name "bed1" }
                            bed { name "bed2" }
                        }
                    }
                    """

    def "parses rooms correctly"() {
        when:
            def house = new DslParser().parse(input)

        then:
            house.rooms.size() == 1
            house.rooms[0].name == "Room 1"
    }

    def "parses beds correctly"() {
        when:
            def house = new DslParser().parse(input)

        then:
            house.rooms[0].beds.size() == 2
            house.rooms[0].beds[0].name == "bed1"
            house.rooms[0].beds[1].name == "bed2"
    }
}

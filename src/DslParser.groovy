class DslParser {

    GroovyShell shell = new GroovyShell();

    def parse(String data){
        def userScript = shell.parse(data)
        userScript.setBinding(new Binding(binding()))
        userScript.run()
    }

    Map binding() {
        return ["house" : { c ->
           return applyClosureOnObject(c, new House())
        }]
    }

    class House {

        String name

        Room[] rooms = [];

        def name(name){
            this.name = name
        }

        def room(closure){
            rooms += applyClosureOnObject(closure, new Room())
        }

    }

    class Room {
        String name
        Bed[] beds = []

        def name(name){
            this.name = name
        }

        def bed(closure) {
            beds += applyClosureOnObject(closure, new Bed())
        }
    }

    class Bed {
        String name

        def name(name){
            this.name = name
        }
    }

    static def applyClosureOnObject(closure, object){
        closure.setDelegate(object)
        closure.setResolveStrategy(Closure.DELEGATE_ONLY)
        closure.call()

        return object
    }
}

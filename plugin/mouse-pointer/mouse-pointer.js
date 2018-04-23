(function (doc, win) {
    "use strict"

    var mouse_pointer_spotlight = {
        position: 'absolute',
        float: 'left',
        borderRadius: '50%',
        width: '75px',
        height: '75px',
        backgroundColor: 'rgba(50, 50, 255, 0.5)',
        zIndex: 20,
        display: 'none'
    }
    var toggleBind = false
    var body = doc.getElementsByTagName('body')[0]
    var tail = doc.createElement('div')

    function initModule() {
        Object.assign(tail.style, mouse_pointer_spotlight)
        body.appendChild(tail)
        setKeyboards()
        if (window.Reveal.registerKeyboardShortcut) {
            Reveal.registerKeyboardShortcut('CAPSLOCK', 'Toggle Mouse Pointer');
        }
    }

    function mouse_pointing(e) {
        tail.style.display = 'block'
        tail.style.left = e.pageX - 15 + 'px'
        tail.style.top = e.pageY - 15 + 'px'
    }

    function toogleMousePointer() {
        if (!toggleBind) {
            document.removeEventListener('mousemove', mouse_pointing)
            tail.style.display = 'none'
            body.style.cursor = 'auto'
        } else {
            tail.style.display = 'block'
            tail.style.width = '75px'
            tail.style.height = '75px'
            body.style.cursor = 'none'
            document.addEventListener('mousemove', mouse_pointing)
        }
    }

    function setKeyboards(params) {
        document.addEventListener('keydown', function (event) {
            if (event.keyCode === 20) {
                event.preventDefault()
                toggleBind = !toggleBind
                toogleMousePointer()
            }
        }, false)
    }


    initModule()
})(document, window)
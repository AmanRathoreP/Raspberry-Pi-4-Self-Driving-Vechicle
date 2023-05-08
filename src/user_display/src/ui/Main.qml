import QtCore
import QtQuick
import QtQuick.Layouts
import QtQuick.Controls
import QtQuick.Controls.Material


ApplicationWindow {
    id: window
    width: 360
    height: 520
    visible: true
    title: "User Display for SDV"

    Shortcut {
        sequences: ["Esc", "Back"]
        enabled: stackView.depth > 1
        onActivated: navigateBackAction.trigger()
    }

    Action {
        id: navigateBackAction
        onTriggered: {
            if (stackView.depth > 1) {
                stackView.pop()
                listView.currentIndex = -1
            } else {
                drawer.open()
            }
        }
    }


    Action {
        id: optionsMenuAction
        onTriggered: optionsMenu.open()
    }

    header: ToolBar {
        RowLayout {
            spacing: 20
            anchors.fill: parent

            ToolButton {
                icon.source: stackView.depth > 1 ? "qrc:/graphics/images/icons/resources/icons/back.svg" : "qrc:/graphics/images/icons/resources/icons/menu.svg"
                action: navigateBackAction
            }

            Label {
                id: titleLabel
                text: listView.currentItem ? listView.currentItem.text : "Home Screen"
                font.pixelSize: 20
                elide: Label.ElideRight
                horizontalAlignment: Qt.AlignHCenter
                verticalAlignment: Qt.AlignVCenter
                Layout.fillWidth: true
            }

            ToolButton {
                icon.source: "qrc:/graphics/images/icons/resources/icons/dot-stack.svg"
                action:optionsMenuAction

                Menu {
                    id: optionsMenu
                    x: parent.width - width
                    transformOrigin: Menu.TopRight

                    Action {
                        text: "Help"
                        icon.source: "qrc:/graphics/images/icons/resources/icons/help.svg"
                        onTriggered:Qt.openUrlExternally("https://github.com/AmanRathoreP/Raspberry-Pi-4-Self-Driving-Vechicle")
                    }
                    Action {
                        text: "About"
                        icon.source: "qrc:/graphics/images/icons/resources/icons/about.svg"
                        onTriggered: aboutDialog.open()
                    }
                    Action {
                        text: "Connection"
                        icon.source: "qrc:/graphics/images/icons/resources/icons/connection.svg"
                        onTriggered: connectionDialog.open()
                    }
                }
            }
        }
    }

    Drawer {
        id: drawer
        width: Math.min(window.width, window.height) / 3 * 2
        height: window.height
        interactive: stackView.depth === 1

        ListView {
            id: listView

            focus: true
            currentIndex: -1
            topMargin: 7
            bottomMargin: 7
            anchors.fill: parent

            delegate: ItemDelegate {
                width: listView.width
                text: model.title
                contentItem: Row {
                    spacing: 10
                    Image {
                        source: model.iconSrc
                        width: parent.height
                        height: parent.height
                    }
                    Text {
                        text: model.title
                        font.pixelSize: 20
                    }
                }
                highlighted: ListView.isCurrentItem
                onClicked: {
                    listView.currentIndex = index
                    stackView.push(Qt.createComponent(model.source))
                    drawer.close()
                }
            }


            model: ListModel {
                ListElement { title: "Vehicle's Logs"; source: "./pages/vehicle-logs.qml"; iconSrc: "qrc:/graphics/images/icons/resources/icons/vehicle-logs.svg" }
                ListElement { title: "App's Logs"; source: "./pages/app-logs.qml"; iconSrc: "qrc:/graphics/images/icons/resources/icons/logs.svg" }
                ListElement { title: "App Settings"; source: "./pages/app-settings.qml"; iconSrc: "qrc:/graphics/images/icons/resources/icons/settings.svg" }
            }

            ScrollIndicator.vertical: ScrollIndicator { }
        }
    }

    StackView {
        id: stackView
        anchors.fill: parent
        initialItem: Qt.createComponent("./pages/home.qml")
    }

    Dialog {
        id: aboutDialog
        modal: true
        title: "About"
        x: (window.width - width) / 2
        y: (window.height - height) / 2
        width: window.width * 0.7
        height: window.height * 0.8
        contentHeight: imageLogo.height * 2.5
        parent: Overlay.overlay

        standardButtons: Dialog.Close

        Flickable {
            id: flickable
            clip: true
            anchors.fill: parent
            contentHeight: aboutColumn.height

            Column {
                id: aboutColumn
                spacing: 20
                width: parent.width

                Image {
                    id: imageLogo
                    width: parent.implicitWidth / 5
                    anchors.horizontalCenter: parent.horizontalCenter
                    fillMode: Image.PreserveAspectFit
                    source: "qrc:/graphics/images/for-app/resources/images/display.svg"
                }

                Label {
                    width: parent.width
                    text: "This apps provides freedom to user to interact with SDV in real time with best performance!"
                    wrapMode: Label.Wrap
                }

                Button{
                    text :"About author"
                    icon.source: "qrc:/graphics/images/icons/resources/icons/author.svg"
                    onClicked: Qt.openUrlExternally("https://github.com/AmanRathoreP/AmanRathoreP")
                    ToolTip {
                        delay: parseInt(myAppSettings.get_value("delayForToolTipsToAppear"))
                        text: "The author of this app is Aman whose GitHub user name is \"AmanRathoreP\""
                        visible: (parent.hovered || parent.pressed) && String(myAppSettings.get_value("showToolTips")).indexOf("t") !== -1 ? true : false
                    }
                }
            }

            ScrollIndicator.vertical: ScrollIndicator {
                parent: aboutDialog.contentItem
                anchors.top: flickable.top
                anchors.bottom: flickable.bottom
                anchors.right: parent.right
                anchors.rightMargin: -aboutDialog.rightPadding + 1
            }
        }
    }

    Dialog {
        id: connectionDialog
        modal: true
        title: "Wireless Connection"
        x: (window.width - width) / 2
        y: (window.height - height) / 2
        width: window.width * 0.7
        height: window.height * 0.8
        contentHeight: imageLogoConnection.height * 2.5
        parent: Overlay.overlay

        standardButtons: Dialog.Close

        Flickable {
            id: flickableConnection
            clip: true
            anchors.fill: parent
            contentHeight: connectionColumn.height

            Column {
                id:  connectionColumn
                spacing: 20
                width: parent.width

                Image {
                    id: imageLogoConnection
                    width: parent.implicitWidth / 5
                    anchors.horizontalCenter: parent.horizontalCenter
                    fillMode: Image.PreserveAspectFit
                    source: "qrc:/graphics/images/for-app/resources/images/connection.svg"
                }

                Label {
                    width: parent.width
                    text: "You need to establish a wireless socket communication in order to start the flight!\nBelow progress bar depicts the status of the connection"
                    wrapMode: Label.Wrap
                }

                Button{
                    text :"Update status"
                    icon.source: "qrc:/graphics/images/icons/resources/icons/update.svg"
                    onClicked: {
                        progressBarConnection.visible = !(communication.getIsConnected());
                        lableConnection.text = communication.getIsConnected() ? "Connected!" : "Waiting for connection...";
                    }
                    ToolTip {
                        delay: parseInt(myAppSettings.get_value("delayForToolTipsToAppear"))
                        text: "Use this button to update the status of the progress bar"
                        visible: (parent.hovered || parent.pressed) && String(myAppSettings.get_value("showToolTips")).indexOf("t") !== -1 ? true : false
                    }
                }

                Label {
                    id: lableConnection
                    width: parent.width
                    text: communication.getIsConnected() ? "Connected!" : "Waiting for connection..."
                }

                ProgressBar {
                    id: progressBarConnection
                    indeterminate: true
                    anchors.horizontalCenter: parent.horizontalCenter
                    visible: !(communication.getIsConnected())
                }

            }

            ScrollIndicator.vertical: ScrollIndicator {
                parent: connectionDialog.contentItem
                anchors.top: flickableConnection.top
                anchors.bottom: flickableConnection.bottom
                anchors.right: parent.right
                anchors.rightMargin: - connectionDialog.rightPadding + 1
            }
        }
    }

}

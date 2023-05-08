import QtQuick
import QtQuick.Controls

Item {
    Row{
        padding: 5
        anchors {
            left: parent.left
            right: parent.right
            bottom: parent.bottom
            top: parent.top
        }
        spacing: 5

        RangeSlider {
            id: speedRangeSlider
            orientation: Qt.Vertical
            from: 0
            to: 65535
            first.value: 26214
            second.value: 62258
            height: parent.height
            width: 50
        }

        Slider {
            orientation: Qt.Vertical
            from: 0
            to: 65535
            value: 0
            height: parent.height
            width: 50
            onValueChanged: {
                if (pressed) {
                    value = 0;
                }
            }
        }

        Column{
            id: reverseColumn
            height: parent.height

            Rectangle{
                height: reverseColumn.height - (reverseSwitch.height*2.5)
                width: reverseColumn.width
  color: "transparent"
                anchors{
                    horizontalCenter: reverseColumn.horizontalCenter
                }
            }

            Label{
                text: "Reverse"
                anchors{
                    horizontalCenter: reverseColumn.horizontalCenter
                }
            }

            Switch {
                id: reverseSwitch
                checked: false // Set the initial value
                anchors{
                    horizontalCenter: reverseColumn.horizontalCenter
                }
            }

        }

        Dial {
            id: steeringDial
            height: parent.height
            value: 0.5
            onValueChanged: {
                  if (!pressed) {
                      // If the dial is not pressed and its value is different from 0.5,
                      // set the value to 0.5
                      value = 0.5;
                  }
              }
        }
    }
}

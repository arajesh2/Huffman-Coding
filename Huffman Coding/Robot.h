/* 
 * File:   Robot.h
 * Author: Aniruddha Rajesh
 *
 * Created on January 25, 2018, 10:14 PM
 */

#ifndef ROBOT_H
#define ROBOT_H
#include <iostream>
#include <vector>
#include <string>
#include "Grid.h"
using namespace std;

class Robot 
{
friend ostream& operator<<(ostream &outputStream, const Robot &paths);

public: 
    Robot(int robotX, int robotY, int treasureX, int treasureY);
    bool getPaths(const int currentRobotx, const int currentRoboty, const string pathTaken);
    ~Robot();

private:
    vector<string> possiblePaths;
    Grid *treasureLocation;
};
#endif /* ROBOT_H */


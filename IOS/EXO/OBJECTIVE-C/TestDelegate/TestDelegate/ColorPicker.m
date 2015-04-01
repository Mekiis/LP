//
//  ColorPicker.m
//  TestDelegate
//
//  Created by iem on 11/12/2014.
//  Copyright (c) 2014 iem. All rights reserved.
//

#import "ColorPicker.h"

@interface ColorPicker ()

@end

@implementation ColorPicker

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)GreenButtonPressed:(id)sender {
    UIColor *green = [[UIColor alloc] initWithRed:26.0/255 green:197.0/255 blue:115.0/255 alpha:1.0];
    [self completionHandler](green);
//    [[self delegate] userDidChooseColor:green];
}

- (IBAction)OrangeButtonPressed:(id)sender {
    UIColor *orange = [[UIColor alloc] initWithRed:238.0/255 green:102.0/255 blue:6.0/255 alpha:1.0];
    [self completionHandler](orange);
//    [[self delegate] userDidChooseColor:orange];
}

- (IBAction)RedButtonPressed:(id)sender {
    UIColor *red = [[UIColor alloc] initWithRed:110.0/255 green:0.0/255 blue:47.0/255 alpha:1.0];
    [self completionHandler](red);
//    [[self delegate] userDidChooseColor:red];
}

@end

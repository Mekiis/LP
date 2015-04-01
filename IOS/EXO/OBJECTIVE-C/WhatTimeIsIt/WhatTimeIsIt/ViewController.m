//
//  ViewController.m
//  WhatTimeIsIt
//
//  Created by iem on 04/12/2014.
//  Copyright (c) 2014 iem. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()
@property (weak, nonatomic) IBOutlet UILabel *mLabel;
@property UInt16 count;

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    _count = 0;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)whatTimeIsIt:(id)sender {
    NSDate *now = [[NSDate alloc] initWithTimeIntervalSinceNow:0.0];
    NSDateFormatter *dateFormatter =[[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"'Le' dd/MM/yyyy à HH:mm:ss"];
    _count++;
    NSString *string = [NSString stringWithFormat:@"%d", _count];
    self.mLabel.text = string;
}

@end

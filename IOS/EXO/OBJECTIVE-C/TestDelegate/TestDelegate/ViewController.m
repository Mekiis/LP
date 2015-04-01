//
//  ViewController.m
//  TestDelegate
//
//  Created by iem on 11/12/2014.
//  Copyright (c) 2014 iem. All rights reserved.
//

#import "ViewController.h"

@interface ViewController (){
    __strong UIColor *oldColor;
}

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)ChangeColor:(id)sender {
}


#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if([[segue identifier] isEqualToString:@"PickColor"]){
        ColorPicker *destinationController = [segue destinationViewController];
        [destinationController setCompletionHandler:^(UIColor *color){
            [self userDidChooseColor:color];
        }];
    }
}

//#pragma mark - Color picker Deletage

-(void)userDidChooseColor:(UIColor *)color{
    oldColor = [[self view] backgroundColor];
    [[self view] setBackgroundColor:color];
    [self dismissViewControllerAnimated:YES completion:nil];
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"New color"
                                                        message:@"Do you like the color ?"
                                                        delegate:self
                                                cancelButtonTitle:@"No"
                                                otherButtonTitles:@"Yes",nil];
    [alertView show];
    
    
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex{
    if (buttonIndex == [alertView cancelButtonIndex]) {
        [[self view] setBackgroundColor:oldColor];
    }
}

- (IBAction)filmFilter:(id)sender {
    NSArray *prenoms = @[@"Obleix", @"YellowSubMarine"];
    
    BOOL (^test)(id obj, NSUInteger idx, BOOL *stop);
    
    test = ^(id obj, NSUInteger idx, BOOL *stop){
        NSString *str = (NSString *)obj;
        if([str caseInsensitiveCompare:@"G"] == NSOrderedDescending)
            return YES;
        else
            return NO;
    };
    
    NSIndexSet *indexes = [prenoms indexesOfObjectsPassingTest:test];
    NSLog(@"%@", indexes);
}

@end
